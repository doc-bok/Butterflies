package com.bokmcdok.butterflies.world.level.levelgen.feature;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.registries.PeacemakerEntityTypeRegistry;
import com.bokmcdok.butterflies.world.entity.animal.PeacemakerCow;
import com.bokmcdok.butterflies.world.entity.monster.PeacemakerButterfly;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructurePiece;

import java.util.function.Predicate;

/**
 * Generates a Peacemaker Lair as a feature.
 */
@SuppressWarnings("deprecation")
public class PeacemakerLair extends Feature<NoneFeatureConfiguration> {

    // Default block states
    private static final BlockState AIR = Blocks.CAVE_AIR.defaultBlockState();
    private static final BlockState COBBLESTONE = Blocks.COBBLESTONE.defaultBlockState();
    private static final BlockState MOSSY_COBBLESTONE = Blocks.MOSSY_COBBLESTONE.defaultBlockState();

    // Constants for min/max sizes
    private static final int FLOOR_Y = -1;
    private static final int INTERIOR_MIN_Y = 0;
    private static final int INTERIOR_MAX_Y = 6;
    private static final int CEILING_Y = INTERIOR_MAX_Y + 1;
    private static final int MAX_CHESTS = 2;
    private static final int CHEST_ATTEMPTS = 3;
    private static final int MAX_BUTTERFLIES = 6;

    /**
     * Helper to get the right offset for carving doors.
     * @param origin The position of the lair.
     * @param alongX Whether the door is aligned with the x-axis.
     * @param xz The x (or z) offset.
     * @param y The y offset.
     * @param zx The z (or x) offset.
     */
    private static void getDoorCarvingOffset(BlockPos.MutableBlockPos position,
                                             BlockPos origin,
                                             boolean alongX,
                                             int xz,
                                             int y,
                                             int zx) {
        if  (alongX) {
            position.set(origin.offset(xz, y, zx));
        } else {
            position.set(origin.offset(zx, y, xz));
        }
    }

    /**
     * Check if a position will be a wall edge.
     * @param x The x-position.
     * @param z The z-position.
     * @param roomBounds The bounds of the room.
     * @return True if the position is a wall edge.
     */
    private static boolean isRoomBoundary(int x, int z, RoomBounds roomBounds) {
        return x == roomBounds.minX || x == roomBounds.maxX || z == roomBounds.minZ || z == roomBounds.maxZ;
    }

    /**
     * Check if a position is supposed to be a wall.
     * @param x The x-position
     * @param z The z-position
     * @param roomBounds The room bounds.
     * @return True if the positon is a wall.
     */
    private static boolean isWall(int x, int y, int z, RoomBounds roomBounds) {
        boolean onOuterBoundary = isRoomBoundary(x, z, roomBounds);

        boolean onInnerXWall = (x == roomBounds.minInnerX || x == roomBounds.maxInnerX)
                && z >= roomBounds.minInnerZ
                && z <= roomBounds.maxInnerZ;

        boolean onInnerZWall = (z == roomBounds.minInnerZ || z == roomBounds.maxInnerZ)
                && x >= roomBounds.minInnerX
                && x <= roomBounds.maxInnerX;

        boolean belowInnerRoof = y < INTERIOR_MAX_Y - 3;
        boolean inInnerCeiling = y == INTERIOR_MAX_Y - 3
                && x > roomBounds.minInnerX && x < roomBounds.maxInnerX
                && z > roomBounds.minInnerZ && z < roomBounds.maxInnerZ;

        return onOuterBoundary
                || (belowInnerRoof && (onInnerXWall || onInnerZWall))
                || inInnerCeiling;
    }

    /**
     * Check if a position is part of the interior.
     * @param x The x-position.
     * @param y The y-position.
     * @param z The z-position.
     * @param roomBounds The room bounds.
     * @return True if the position is inside the lair.
     */
    private static boolean isInteriorAirSpace(int x, int y, int z, RoomBounds roomBounds) {
        return !isWall(x, y, z, roomBounds)
                && y > FLOOR_Y && y < CEILING_Y;
    }

    /**
     * Construction
     * @param config The configuration for the feature.
     */
    public PeacemakerLair(Codec<NoneFeatureConfiguration> config) {
        super(config);
    }

    /**
     * Try and place a Peacemaker Lair.
     * @param config The configuration for the feature.
     * @return True if the feature was successfully placed.
     */
    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> config) {
        WorldGenLevel level = config.level();
        BlockPos origin = config.origin();
        RandomSource random = config.random();
        Predicate<BlockState> replaceable = Feature.isReplaceable(BlockTags.FEATURES_CANNOT_REPLACE);

        RoomBounds bounds = new RoomBounds(random);
        if (!canGenerate(level, origin, bounds)) {
            return false;
        }

        carveAndBuild(level, origin, bounds, random, replaceable);
        placeChests(level, origin, bounds, random, replaceable);

        spawnPeacemakerButterflies(level, origin, bounds, random);
        spawnPeacemakerCow(level, origin);

        return true;
    }

    /**
     * Class to hold the room bounds.
     */
    private static final class RoomBounds {
        private static final int BASE_ROOM_SIZE = 6;

        private final int baseWidth;
        private final int minX;
        private final int maxX;
        private final int baseLength;
        private final int minZ;
        private final int maxZ;

        private final int minInnerX;
        private final int minInnerZ;
        private final int maxInnerX;
        private final int maxInnerZ;

        /**
         * Construct a new room bounds.
         * @param random A random number generator.
         */
        public RoomBounds(RandomSource random) {
            baseWidth = random.nextInt(2) + BASE_ROOM_SIZE;
            minX = -baseWidth - 1;
            maxX = baseWidth + 1;
            baseLength = random.nextInt(2) + BASE_ROOM_SIZE;
            minZ = -baseLength - 1;
            maxZ = baseLength + 1;

            minInnerX = minX + 4;
            maxInnerX = maxX - 4;
            minInnerZ = minZ + 4;
            maxInnerZ = maxZ - 4;
        }
    }

    /**
     * Check we can generate a Peacemaker Lair.
     * @param level The level being generated.
     * @param origin The position of the lair.
     * @param roomBounds The bounds of the lair.
     * @return True if the Peacemaker Lair can be placed.
     */
    private boolean canGenerate(WorldGenLevel level,
                                BlockPos origin,
                                RoomBounds roomBounds) {

        // Don't generate if hostile butterflies have been disabled.
        if (!ButterfliesConfig.Common.enableHostileButterflies.get()) {
            return false;
        }

        int numOpenings = 0;

        // Test if we can place the room.
        for(int offsetX = roomBounds.minX; offsetX <= roomBounds.maxX; ++offsetX) {
            for(int offsetY = FLOOR_Y; offsetY <= CEILING_Y; ++offsetY) {
                for(int offsetZ = roomBounds.minZ; offsetZ <= roomBounds.maxZ; ++offsetZ) {
                    BlockPos posOffset = origin.offset(offsetX, offsetY, offsetZ);

                    // minY and maxY must be solid throughout
                    boolean isSolid = level.getBlockState(posOffset).isSolid();
                    if (offsetY == FLOOR_Y && !isSolid) {
                        return false;
                    }

                    if (offsetY == CEILING_Y && !isSolid) {
                        return false;
                    }

                    // There must be between 1 and 5 2-high openings
                    if (isRoomBoundary(offsetX, offsetZ, roomBounds)
                            && offsetY == INTERIOR_MIN_Y
                            && level.isEmptyBlock(posOffset)
                            && level.isEmptyBlock(posOffset.above())) {
                        ++numOpenings;
                    }
                }
            }
        }

        return numOpenings >= 1 && numOpenings <= 5;
    }

    /**
     * Carve out and build the Peacemaker Lair
     * @param level The level being generated.
     * @param origin The position of the lair.
     * @param roomBounds The bounds of the lair.
     * @param random  A random number generator.
     * @param replaceable A predicate to check if a block is replaceable.
     */
    private void carveAndBuild(WorldGenLevel level,
                               BlockPos origin,
                               RoomBounds roomBounds,
                               RandomSource random,
                               Predicate<BlockState> replaceable) {
        BlockPos.MutableBlockPos position = new BlockPos.MutableBlockPos();
        for(int offsetX = roomBounds.minX; offsetX <= roomBounds.maxX; ++offsetX) {
            for(int offsetY = INTERIOR_MAX_Y; offsetY >= FLOOR_Y; --offsetY) { // Start from the top and build down
                for(int offsetZ = roomBounds.minZ; offsetZ <= roomBounds.maxZ; ++offsetZ) {

                    position.set(origin.offset(offsetX, offsetY, offsetZ));
                    BlockState posOffsetBlockState = level.getBlockState(position);

                    // If not on outer edges then set block to cave air
                    if (isInteriorAirSpace(offsetX, offsetY, offsetZ, roomBounds)) {
                        if (!posOffsetBlockState.is(Blocks.CHEST) && !posOffsetBlockState.is(Blocks.SPAWNER)) {
                            safeSetBlock(level, position, AIR, replaceable);
                        }

                        // If non-solid below, air above
                    } else if (position.getY() >= level.getMinBuildHeight()
                            && !level.getBlockState(position.below()).isSolid()) {
                        level.setBlock(position, AIR, 2);

                        // Build the walls
                    } else if (posOffsetBlockState.isSolid()
                            && !posOffsetBlockState.is(Blocks.CHEST)) {

                        // Randomly place mossy/non-mossy cobblestone
                        if (offsetY == FLOOR_Y && random.nextInt(4) != 0) {
                            safeSetBlock(level, position, MOSSY_COBBLESTONE, replaceable);
                        } else {
                            safeSetBlock(level, position, COBBLESTONE, replaceable);
                        }
                    }
                }
            }
        }

        carveDoor(level, origin, roomBounds, random, replaceable);
    }

    /**
     * Carve a door into the Peacemaker Cow's chamber.
     * @param level The level being generated.
     * @param origin The position of the lair.
     * @param roomBounds The bounds of the lair.
     * @param random  A random number generator.
     * @param replaceable A predicate to check if a block is replaceable.
     */
    private void carveDoor(WorldGenLevel level,
                           BlockPos origin,
                           RoomBounds roomBounds,
                           RandomSource random,
                           Predicate<BlockState> replaceable) {
        boolean alongX = random.nextBoolean();
        boolean useMinSide = random.nextBoolean();

        int xz;
        if (alongX) {
            xz = useMinSide ? roomBounds.minInnerX : roomBounds.maxInnerX;
        } else {
            xz = useMinSide ? roomBounds.minInnerZ : roomBounds.maxInnerZ;
        }

        BlockPos.MutableBlockPos position = new BlockPos.MutableBlockPos();
        for (int y = 0; y < 2; ++y) {
            for (int zx = -1; zx <= 1; ++zx) {
                getDoorCarvingOffset(position, origin, alongX, xz, y, zx);
                if (level.getBlockState(position).isSolid()) {
                    safeSetBlock(level, position, AIR, replaceable);
                }
            }
        }
    }

    /**
     * Try to place up to two chests in the Lair.
     * @param level The level being generated.
     * @param origin The position of the lair.
     * @param roomBounds The bounds of the lair.
     * @param random  A random number generator.
     * @param replaceable A predicate to check if a block is replaceable.
     */
    private void placeChests(WorldGenLevel level,
                             BlockPos origin,
                             RoomBounds roomBounds,
                             RandomSource random,
                             Predicate<BlockState> replaceable) {

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for(int chestIndex = 0; chestIndex < MAX_CHESTS; ++chestIndex) {
            for(int attemptIndex = 0; attemptIndex < CHEST_ATTEMPTS; ++attemptIndex) {
                int posX = origin.getX() + random.nextInt(roomBounds.baseWidth * 2 + 1) - roomBounds.baseWidth;
                int posY = origin.getY();
                int posZ = origin.getZ() + random.nextInt(roomBounds.baseLength * 2 + 1) - roomBounds.baseLength;
                pos.set(posX, posY, posZ);
                if (level.isEmptyBlock(pos)) {
                    int solidAdjacentWalls = 0;

                    for(Direction direction : Direction.Plane.HORIZONTAL) {
                        if (level.getBlockState(pos.relative(direction)).isSolid()) {
                            ++solidAdjacentWalls ;
                        }
                    }

                    if (solidAdjacentWalls  == 1) {
                        safeSetBlock(level, pos, StructurePiece.reorient(level, pos, Blocks.CHEST.defaultBlockState()), replaceable);
                        RandomizableContainerBlockEntity.setLootTable(level, random, pos, new ResourceLocation(ButterfliesMod.MOD_ID, "chests/peacemaker_lair"));
                        break;
                    }
                }
            }
        }
    }

    /**
     * Spawn the Peacemaker Cow.
     * @param level The level being generated.
     * @param origin The position of the lair.
     */
    @SuppressWarnings("OverrideOnly")
    private void spawnPeacemakerCow(WorldGenLevel level,
                                    BlockPos origin) {
        EntityType<?> entityType = PeacemakerEntityTypeRegistry.PEACEMAKER_COW.get();
        Entity entity = entityType.create(level.getLevel());
        if (entity instanceof PeacemakerCow cow) {

            cow.moveTo(origin.getX(), origin.getY(), origin.getZ(), 0.0F, 0.0F);
            cow.setPersistenceRequired();
            cow.finalizeSpawn(level,
                    level.getCurrentDifficultyAt(origin),
                    MobSpawnType.NATURAL,
                    null,
                    null);

            level.addFreshEntity(cow);
        }
    }

    /**
     * Spawn some Peacemaker Butterflies.
     * @param level The level being generated.
     * @param origin The position of the lair.
     */
    private void spawnPeacemakerButterflies(WorldGenLevel level,
                                            BlockPos origin,
                                            RoomBounds roomBounds,
                                            RandomSource random) {
        for (int i = 0; i < MAX_BUTTERFLIES; ++i) {
            EntityType<?> entityType = PeacemakerEntityTypeRegistry.PEACEMAKER_BUTTERFLY.get();
            Entity entity = entityType.create(level.getLevel());
            if (entity instanceof PeacemakerButterfly butterfly) {

                int x = origin.getX() + (random.nextBoolean() ? 4 + random.nextInt(roomBounds.maxX - 4) : -4 - random.nextInt(-roomBounds.minX + 4));
                int z = origin.getZ() + (random.nextBoolean() ? 4 + random.nextInt(roomBounds.maxZ - 4) : -4 - random.nextInt(-roomBounds.minZ + 4));

                butterfly.moveTo(x, origin.getY(), z, 0.0F, 0.0F);
                butterfly.setPersistenceRequired();
                butterfly.finalizeSpawn(level,
                        level.getCurrentDifficultyAt(origin),
                        MobSpawnType.NATURAL,
                        null,
                        null);

                level.addFreshEntity(butterfly);
            }
        }
    }
}