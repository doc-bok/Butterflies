package com.bokmcdok.butterflies.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import java.util.function.Predicate;

/**
 * Generates a Peacemaker Lair as a feature.
 */
public class PeacemakerLair extends Feature<NoneFeatureConfiguration> {

    // Default block states
    private static final BlockState AIR = Blocks.CAVE_AIR.defaultBlockState();
    private static final BlockState COBBLESTONE = Blocks.COBBLESTONE.defaultBlockState();
    private static final BlockState MOSSY_COBBLESTONE = Blocks.MOSSY_COBBLESTONE.defaultBlockState();

    // Constants for min/max sizes
    private static final int FLOOR_Y = -1;
    private static final int INTERIOR_MIN_Y = 0;
    private static final int INTERIOR_MAX_Y = 3;
    private static final int CEILING_Y = 4;
    private static final int MAX_CHESTS = 2;
    private static final int CHEST_ATTEMPTS = 3;

    /**
     * Check if a position will be a wall edge.
     * @param x The x-position.
     * @param z The z-position.
     * @param minX The minimum x-position.
     * @param maxX The maximum x-position.
     * @param minZ The minimum z-position.
     * @param maxZ The maximum z-position.
     * @return True if the position is a wall edge.
     */
    private static boolean isWallEdge(int x, int z, int minX, int maxX, int minZ, int maxZ) {
        return x == minX || x == maxX || z == minZ || z == maxZ;
    }

    /**
     * Check if a position is part of the interior.
     * @param x The x-position.
     * @param y The y-position.
     * @param z The z-position.
     * @param minX The minimum x-position.
     * @param maxX The maximum x-position.
     * @param minZ The minimum z-position.
     * @param maxZ The maximum z-position.
     * @return True if the position is inside the lair.
     */
    private static boolean isInterior(int x, int y, int z, int minX, int maxX, int minZ, int maxZ) {
        return x != minX && x != maxX
                && z != minZ && z != maxZ
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
        return true;
    }

    /**
     * Class to hold the room bounds.
     */
    private static final class RoomBounds {
        private final int baseWidth;
        private final int minOffsetX;
        private final int maxOffsetX;
        private final int baseLength;
        private final int minOffsetZ;
        private final int maxOffsetZ;

        /**
         * Construct a new room bounds.
         * @param random A random number generator.
         */
        public RoomBounds(RandomSource random) {
            baseWidth = random.nextInt(2) + 2;
            minOffsetX = -baseWidth - 1;
            maxOffsetX = baseWidth + 1;
            baseLength = random.nextInt(2) + 2;
            minOffsetZ = -baseLength - 1;
            maxOffsetZ = baseLength + 1;
        }
    }

    /**
     * Check we can generate a Peacemaker Lair.
     * @param level The level being generated.
     * @param origin The position of the lair.
     * @param roomBounds The bounds of the lair.
     * @return True if the Peacemaker Lair can be placed.
     */
    @SuppressWarnings("deprecation")
    private boolean canGenerate(WorldGenLevel level,
                                BlockPos origin,
                                RoomBounds roomBounds) {
        int numOpenings = 0;

        // Test if we can place the room.
        for(int offsetX = roomBounds.minOffsetX; offsetX <= roomBounds.maxOffsetX; ++offsetX) {
            for(int offsetY = FLOOR_Y; offsetY <= CEILING_Y; ++offsetY) {
                for(int offsetZ = roomBounds.minOffsetZ; offsetZ <= roomBounds.maxOffsetZ; ++offsetZ) {
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
                    if (isWallEdge(offsetX, offsetZ, roomBounds.minOffsetX, roomBounds.maxOffsetX, roomBounds.minOffsetZ, roomBounds.maxOffsetZ)
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
     * Check we can generate a Peacemaker Lair.
     * @param level The level being generated.
     * @param origin The position of the lair.
     * @param roomBounds The bounds of the lair.
     * @param random  A random number generator.
     * @param replaceable A predicate to check if a block is replaceable.
     */
    @SuppressWarnings("deprecation")
    private void carveAndBuild(WorldGenLevel level,
                               BlockPos origin,
                               RoomBounds roomBounds,
                               RandomSource random,
                               Predicate<BlockState> replaceable) {
        BlockPos.MutableBlockPos posOffset = new BlockPos.MutableBlockPos();
        for(int offsetX = roomBounds.minOffsetX; offsetX <= roomBounds.maxOffsetX; ++offsetX) {
            for(int offsetY = INTERIOR_MAX_Y; offsetY >= FLOOR_Y; --offsetY) { // Start from the top and build down
                for(int offsetZ = roomBounds.minOffsetZ; offsetZ <= roomBounds.maxOffsetZ; ++offsetZ) {

                    posOffset.set(origin.offset(offsetX, offsetY, offsetZ));
                    BlockState posOffsetBlockState = level.getBlockState(posOffset);

                    // If not on outer edges then set block to cave air
                    if (isInterior(offsetX, offsetY, offsetZ, roomBounds.minOffsetX, roomBounds.maxOffsetX, roomBounds.minOffsetZ, roomBounds.maxOffsetZ)) {
                        if (!posOffsetBlockState.is(Blocks.CHEST) && !posOffsetBlockState.is(Blocks.SPAWNER)) {
                            this.safeSetBlock(level, posOffset, AIR, replaceable);
                        }

                        // If non-solid below, air above
                    } else if (posOffset.getY() >= level.getMinBuildHeight()
                            && !level.getBlockState(posOffset.below()).isSolid()) {
                        level.setBlock(posOffset, AIR, 2);

                        // Build the walls
                    } else if (posOffsetBlockState.isSolid()
                            && !posOffsetBlockState.is(Blocks.CHEST)) {

                        // Randomly place mossy/non-mossy cobblestone
                        if (offsetY == FLOOR_Y && random.nextInt(4) != 0) {
                            this.safeSetBlock(level, posOffset, MOSSY_COBBLESTONE, replaceable);
                        } else {
                            this.safeSetBlock(level, posOffset, COBBLESTONE, replaceable);
                        }
                    }
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
    @SuppressWarnings("deprecation")
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
                    int solidAdjacentWalls  = 0;

                    for(Direction direction : Direction.Plane.HORIZONTAL) {
                        if (level.getBlockState(pos.relative(direction)).isSolid()) {
                            ++solidAdjacentWalls ;
                        }
                    }

                    if (solidAdjacentWalls  == 1) {
                        this.safeSetBlock(level, pos, StructurePiece.reorient(level, pos, Blocks.CHEST.defaultBlockState()), replaceable);
                        RandomizableContainerBlockEntity.setLootTable(level, random, pos, BuiltInLootTables.SIMPLE_DUNGEON);
                        break;
                    }
                }
            }
        }
    }
}