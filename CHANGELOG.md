## Changelog

### 5.2.0 (2024-08-26)
- Added the lepidopterist.

### 5.1.1 (2024-08-20)
- Butterfly data is now the source of truth for data generation.

### 5.1.0 (2024-08-20)
- Added the butterfly feeder.

### 5.0.5 (2024-08-18)
- More refactoring to remove static registry references.

### 5.0.4 (2024-08-18)
- Refactored code to rely less on static variables.

### 5.0.3 (2024-08-18)
- Fix plateau and plain biome modifiers being confused.

### 5.0.2 (2024-08-18)
- Biome modifiers can now be generated.

### 5.0.1 (2024-08-14)
- Fixed incorrect syncing from client to server.

### 5.0.0 (2024-08-11)
- Release v5.0.0

### 4.19.0 (2024-08-11)
- Added the Blue Moon Butterfly.

### 4.18.0 (2024-08-10)
- Added the Common Birdwing Butterfly.
- Added support for `HUGE` butterflies.

### 4.17.0 (2024-08-10)
- Added the Commander Butterfly.

### 4.16.0 (2024-08-09)
- Added the Common Grass Yellow Butterfly.

### 4.15.3 (2024-08-05)
- Peacock butterflies can pollinate sweet berry bushes.

### 4.15.2 (2024-08-05)
- Monarch Caterpillars can be brewed into poison.

### 4.15.1 (2024-08-05)
- Heath butterflies now use `MothWanderGoal`.

### 4.15.0 (2024-08-05)
- Cats and forester butterflies are no longer enemies.

### 4.14.1 (2024-08-05)
- Emperor and Hairstreak butterflies can now use wood blocks.

### 4.14.0 (2024-08-05)
- Added Clipper Butterfly variants.

### 4.13.3 (2024-08-05)
- Cabbage butterflies are now fast.

### 4.13.2 (2024-08-05)
- Buckeye butterflies can now use melon and pumpkin blocks.

### 4.13.1 (2024-08-05)
- Admiral butterflies are now crepuscular.

### 4.13.0 (2024-08-05)
- Added butterfly breeding.

### 4.12.0 (2024-08-03)
- Added the Diamondback Moth.

### 4.11.3 (2024-08-03)
- Fixed mating goal fertilising butterflies with zero eggs.

### 4.11.2 (2024-08-03)
- Fixed missing `spongymale` textures and models.

### 4.11.1 (2024-08-03)
- Added Infested Apples.

### 4.11.0 (2024-08-03)
- Added the Codling Moth.

### 4.10.0 (2024-08-02)
- Added the Atlas and Carpet Moths.

### 4.9.0 (2024-07-29)
- Added support for dimorphic species.
- Added the Spongy Moth.

### 4.8.0 (2024-07-28)
- Added the Indian Meal Moth.
- New goal for butterflies/moths that eat plants.
- Hay bales are now an option for butterflies to use as landing blocks. 
- Fixed the spawn code so it takes advantage of `extraLandingBlocks`.

### 4.7.0 (2024-07-25)
- Added the Peppered Moth.

### 4.6.0 (2024-07-25)
- Added the Domestic Silk Moth.

### 4.5.4 (2024-07-23)
- Some minor adjustments to butterfly animations when landed.

### 4.5.3 (2024-07-21)
- Fixed butterfly landing state not being saved correctly.

### 4.5.2 (2024-07-20)
- Fixed directional creatures not getting their spawn block properly.

### 4.5.1 (2024-07-17)
- Fixed some advancement related bugs.

### 4.5.0 (2024-07-15)
- Added the Clothes Moth.

### 4.4.0 (2024-07-14)
- Added silk.

### 4.3.0 (2024-07-11)
- Moths will spread their wings when they land rather than hold them straight
up.

### 4.2.0 (2024-07-11)
- Moths will now be drawn to brighter areas.

### 4.1.0 (2024-07-02)
- Added diurnality to butterflies.

### 4.0.1 (2024-07-05)
- Fixed bounding box for butterflies, so they won't escape bottles.

### 4.0.0 (2024-07-01)
- Pollination Release of Bok's Banging Butterflies.

### 3.6.0 (2024-07-01)
- Added the Ice Butterfly.

### 3.5.2 (2024-06-28)
- Improved butterfly data reading.

### 3.5.1 (2024-06-28)
- Fixed butterflies colliding with the ground too often.

### 3.5.0 (2024-06-28)
- Butterflies will now avoid other entities.

### 3.4.12 (2024-06-28)
- Butterflies now mate using a goal rather than by pure chance.

### 3.4.11 (2024-06-21)
- Torchflowers will now use the vanilla crops and require farmland to spread.
- Config option to disable the pollination entirely.
- Added preferred flowers to butterfly book descriptions.

### 3.4.10 (2024-06-18)
- Butterflies will pollinate flowers and cause new ones to grow.
- Flower crops will bloom into flowers.
- Some fixes for butterfly bounding boxes.
- Fixed goals not having access to butterfly data when created.

### 3.4.9 (2024-06-11)
- Added new AI goals for butterfly landings.

### 3.4.8 (2024-06-03)
- Fixed species extraction for butterfly entities.

### 3.4.7 (2024-06-03)
- Butterfly registration now happens automatically.

### 3.4.6 (2024-05-31)
- Butterfly based advancements can now be generated.

### 3.4.5 (2024-05-26)
- Reduced number of localisation strings needed.
- Can now generate localisation strings.

### 3.4.4 (2024-05-26)
- Texture locations for butterfly scrolls are automatically generated.

### 3.4.3 (2024-05-26)
- Frog food JSON data can now be generated.

### 3.4.2 (2024-05-25)
- Fixed the Butterfly Book challenge.

### 3.4.1 (2024-05-23)
- Added the Peacock Butterfly.

### 3.4.0 (2024-04-06)
- Compatibility Release.

### 3.3.3 (2024-04-01)
- Fixed butterfly tag code that was accidentally removed.

### 3.3.2 (2024-03-20)
- Added tooltips to butterfly items.

### 3.3.1 (2024-03-20)
- Butterflies now release heart particles when they become fertile.

### 3.3.0 (2024-03-08)
- Reworked biome modifiers, so butterflies can spawn in other mod's biomes.

### 3.2.2 (2024-03-14)
- Advancements now work with new items.

### 3.2.1 (2024-03-12)
- Rewrote butterfly items and recipes, so they don't rely on NBT data to work.

### 3.2.0 (2024-03-08)
- Butterfly eggs now spawn as entities.

### 3.1.1 (2024-03-19)
- Fixed server crash when destroying bottled butterflies.

### 3.1.0 (2024-03-02)
- Butterflies now need to 'mate' before they can lay eggs.
- Butterfly movement now allows more freedom.
- Frogs will now eat butterflies.
- Butterflies have more predators in general.
- Butterflies now have a limited number of eggs.
- Butterfly breeding will be disabled in areas where they are too dense.
- Added server config options to help manage butterfly growth.

### 3.0.3 (2024-02-29)
- Caterpillar items now get added to the inventory on the server side.

### 3.0.2 (2024-02-16)
- Caterpillars remembered how to fall.

### 3.0.1 (2024-02-16)
- Fixed Butterfly Data not being synced with clients correctly.

### 3.0.0 (2024-02-15)
- Caterpillar Release.

### 2.4.0 (2024-02-12)
- Caterpillars can now be stored in jars.

### 2.3.0 (2024-02-08)
- Caterpillars and Chrysalises will now spawn naturally.

### 2.2.2 (2024-02-04)
- Advancements for collecting caterpillars.

### 2.2.1 (2024-02-04)
- Caterpillars that end up on non-leaf blocks will starve to death instead of
    creating a chrysalis.

### 2.2.0 (2024-02-04)
- Caterpillars can be picked up and moved by players.

### 2.1.1 (2024-02-07)
- Expanded culling boxes for butterfly entities for compatibility with optimisation mods.

### 2.1.0 (2024-01-28)
- Butterfly data can now be edited via JSON files.

### 2.0.9 (2024-01-26)
- Fixed butterfly net achievements not working.

### 2.0.8 (2024-01-14)
- Redesigned the buckeye butterfly texture.
- Tweaked cabbage and forester textures.

### 2.0.7 (2024-01-13)
- Butterfly eggs are now smaller.

### 2.0.6 (2024-01-05)
- Fixed shift-clicking recipes.

### 2.0.5 (2024-01-05)
- Added a secret goal.

### 2.0.4 (2024-01-05)
- Mobs spawned using spawn eggs will now be persistent.

### 2.0.3 (2024-01-05)
- Updated data pack format to 18 (correct version for 1.20.2).

### 2.0.2 (2024-01-04)
- Fix butterfly azalea leaves not having a model.

### 2.0.1 (2023-12-17)
- Fix crash when launching server.

### 2.0.0 (2023-12-15)
- Second release of the mod.

### 1.5.0 (2023-12-15)
- Zhuangzi

### 1.4.1 (2023-12-15)
- Updated to Forge v48.1.0 (Minecraft v1.20.2)

### 1.4.0 (2023-12-15)
- Added the butterfly book

### 1.3.1 (2023-12-08)
- Fixed rendering of butterfly scrolls

### 1.3.0 (2023-12-08)
- Modified sizes of butterflies so they're closer to reality
- Some butterflies will now move faster
- Butterflies now have varying rarities
- Caterpillars, chrysalises, and butterflies now have varying lifespans

### 1.2.0  (2023-11-30)
- Snow.

### 1.1.4  (2023-11-30)
- Butterflies can now spawn in cherry groves.

### 1.1.3  (2023-11-30)
- Caterpillar and chrysalis size is now affected by species and age.

### 1.1.2  (2023-11-30)
- Use ResourceLocations instead of strings for spawning butterfly entities.

### 1.1.1  (2023-11-12)
- Butterfly scrolls can now be placed around the world.

### 1.1.0  (2023-11-05)
- Added butterfly scrolls.

### 1.0.4 (2023-11-23)
- Fixed crash when hovering over full butterfly net.

### 1.0.3 (2023-11-22)
- Fixed caterpillar and chrysalis aging.

### 1.0.2 (2023-11-06)
- Added names to the entities to better support mod packs.

### 1.0.1 (2023-10-29)
- Updated Forge to current recommended version (47.2.0).

### 1.0.0 (2023-10-28)
- First release of the butterfly mod.

### 0.8.5 (2023-10-28)
- Chrysalises will now spawn in the correct position.

### 0.8.4 (2023-10-15)
- Moved butterfly eggs to Spawn Eggs section of creative inventory.

### 0.8.3 (2023-10-15)
- Made butterfly egg-laying 16 times rarer.

### 0.8.2 (2023-10-15)
- Fixed emperor chrysalis texture.

### 0.8.1 (2023-10-15)
- Butterflies et al. are now `Animal`s instead of `AmbientCreature`s.

### 0.8.0 (2023-09-30)
- Cats now attack butterflies.

### 0.7.1 (2023-09-30)
- Created textures for chrysalises.

### 0.7.0 (2023-09-25)
- First pass Chrysalis implementation

### 0.6.6 (2023-09-15)
- Caterpillars can now crawl on surfaces in all directions.

### 0.6.5 (2023-09-13)
- Fixed mangrove and cherry leaf blocks not spawning caterpillars.

### 0.6.4 (2023-09-09)
- Fixed immortal butterflies being spawned if a bottled butterfly is destroyed
    by lava.

### 0.6.3 (2023-09-09)
- Added multiple textures for bottled butterflies.

### 0.6.2 (2023-09-08)
- Added advancements for collecting butterfly eggs.

### 0.6.1 (2023-09-08)
- Fixed JSON format of achievements.

### 0.6.0 (2023-09-08)
- Added caterpillars.

### 0.5.0 (2023-08-19)
- Added butterfly eggs.

### 0.4.2 (2023-08-16)
- Bottled butterfly recipe now unlocks if a player has both glass bottle(s) and
    a butterfly net in their inventory.

### 0.4.1 (2023-08-11)
- Nets and bottles will now describe if they are empty.

### 0.4.0 (2023-08-09)
- Added bottled butterflies

### 0.3.1 (2023-07-24)
- Updated Forge to version 47.1.42 (supports Minecraft 1.20.1)

### 0.3.0 (2023-07-19)
- Updated Forge to version 47.0.35 (supports Minecraft 1.20.1)

### 0.2.1 (2023-07-19)
- Fixed butterfly release code.
- Reduced butterfly speed to make them easier to catch.
- Made fishing rod recipe a bit more readable.

### 0.1.12 (2023-07-17)
- Added the butterfly net.

### 0.1.11 (2023-06-02)
- Added the Clipper and Buckeye Butterflies.

### 0.1.10 (2023-05-31)
- Added the Longwing Butterfly.

### 0.1.9 (2023-05-31)
- Added the Admiral Butterfly.

### 0.1.8 (2023-05-06)
- Butterfly bodies now face the right way.

### 0.1.7 (2023-05-06)
- Replaced Common Butterfly with Birdwing Butterfly.

### 0.1.6 (2023-05-05)
- Added the Cabbage Butterfly.

### 0.1.5 (2023-05-05)
- Added the Monarch Butterfly.

### 0.1.4 (2023-05-05)
- Butterflies will now spawn in different biomes depending on type.

### 0.1.3 (2023-05-05)
- Renamed method in ItemRegistry to be more descriptive.

### 0.1.2 (2023-05-05)
- Butterflies can now spawn on leaves.

### 0.1.1 (2023-05-03)
- Update to Forge 1.19.4.

### 0.1.0 (2023-05-02)
- Created butterfly entities that can spawn in the world.

### 0.0.0 (2023-02-01)
- Basic configuration and setup for the mod.
