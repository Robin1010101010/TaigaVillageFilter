import com.seedfinding.mcbiome.biome.Biomes;
import com.seedfinding.mcbiome.source.BiomeSource;
import com.seedfinding.mcbiome.source.OverworldBiomeSource;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.state.Dimension;
import com.seedfinding.mccore.util.data.Pair;
import com.seedfinding.mccore.util.math.DistanceMetric;
import com.seedfinding.mccore.util.pos.BPos;
import com.seedfinding.mccore.util.pos.CPos;
import com.seedfinding.mccore.version.MCVersion;
import com.seedfinding.mcfeature.loot.item.ItemStack;
import com.seedfinding.mcfeature.loot.item.Items;
import com.seedfinding.mcfeature.misc.SpawnPoint;
import com.seedfinding.mcfeature.structure.BastionRemnant;
import com.seedfinding.mcfeature.structure.Fortress;
import com.seedfinding.mcfeature.structure.Village;
import com.seedfinding.mcterrain.terrain.OverworldTerrainGenerator;
import profotoce59.properties.VillageGenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.Random;


public class TaigaVillageFinder {
    public void findSeeds() {
        // Find a seed with a taiga village near spawn with at least 10 obsidian and materials for iron pick and flint and steel in chests

        System.out.println("Starting finder");

        final String fileName = "TaigaVillageFilterSeeds.txt";
        FileWriter fw;
        BufferedWriter bw;

        final MCVersion version = MCVersion.v1_16_1;
        ChunkRand rand = new ChunkRand();
        Village village = new Village(version);
        VillageGenerator vg = new VillageGenerator(version);
        BastionRemnant bastionRemnant = new BastionRemnant(version);
        Fortress fortress = new Fortress(version);
        long worldSeed;
        Random seedRand = new Random();

        while (true) {
            worldSeed = seedRand.nextLong();

            // check if village is at most 16 chunks away from (0, 0)
            CPos vPos = village.getInRegion(worldSeed, 0, 0, rand);
            if (vPos.distanceTo(CPos.ZERO, DistanceMetric.CHEBYSHEV) > 16) continue;

            // check if bastion is at most 16 chunks away from (0, 0)
            CPos bPos = null;
            boolean bastionFound = false;
            for (int xChunk = -1; xChunk < 1; xChunk++) {
                for (int zChuck = -1; zChuck < 1; zChuck++) {
                    bPos = bastionRemnant.getInRegion(worldSeed, xChunk, zChuck, rand);
                    if (bPos != null && bPos.distanceTo(CPos.ZERO, DistanceMetric.CHEBYSHEV) <= 16) {
                        bastionFound = true;
                        break;
                    }
                }
                if (bastionFound) break;
            }
            if (!bastionFound) continue;

            // check if fortress is at most 16 chunks away from bastion
            CPos fPos = null;
            boolean fortressFound = false;
            for (int xChunk = -1; xChunk < 1; xChunk++) {
                for (int zChuck = -1; zChuck < 1; zChuck++) {
                    fPos = fortress.getInRegion(worldSeed, xChunk, zChuck, rand);
                    if (fPos != null && fPos.distanceTo(bPos, DistanceMetric.CHEBYSHEV) <= 16) {
                        fortressFound = true;
                        break;
                    }
                }
                if (fortressFound) break;
            }
            if (!fortressFound) continue;

            // check if village can spawn and is taiga village
            BiomeSource obs = BiomeSource.of(Dimension.OVERWORLD, version, worldSeed);
            if (!village.canSpawn(vPos, obs) || (village.getBiome() != Biomes.TAIGA)) continue;

            // check if bastion can spawn
            BiomeSource nbs = BiomeSource.of(Dimension.NETHER, version, worldSeed);
            if (!bastionRemnant.canSpawn(bPos, nbs) || (bastionRemnant.getBiome() == Biomes.BASALT_DELTAS)) continue;

            // check if fortress can spawn
            if (!fortress.canSpawn(fPos, nbs)) continue;

            // check if village can generate
            OverworldTerrainGenerator otg = new OverworldTerrainGenerator(obs);
            if (!vg.generate(otg, vPos, rand)) continue;

            // check if all village chest combined have at least 10 obsidian
            // and the materials to craft an iron pickaxe and a flint and steel
            List<Pair<BPos, List<ItemStack>>> chests = vg.generateLoot(otg, rand);
            int obsidian = 0;
            int iron = 0;
            int diamonds = 0;
            boolean ironPickaxeFound = false;
            for (Pair<BPos, List<ItemStack>> chest : chests) {
                for (ItemStack item : chest.getSecond()) {
                    if (item.getItem().equals(Items.OBSIDIAN)) {
                        obsidian += item.getCount();
                    }
                    if (item.getItem().equals(Items.IRON_INGOT)) {
                        iron += item.getCount();
                    } else if (!ironPickaxeFound && (item.getItem().equals(Items.IRON_PICKAXE) || item.getItem().equals(Items.DIAMOND))) {
                        ironPickaxeFound = true;
                        iron += 3;
                    } else if (!ironPickaxeFound && (item.getItem().equals(Items.DIAMOND))) {
                        diamonds += item.getCount();
                        if (diamonds >= 3) {
                            ironPickaxeFound = true;
                            iron += 3;
                        }
                    }
                }
            }
            if (obsidian < 10 || iron < 4) continue;

            // check if village is at most 8 chunks away from spawnpoint
            CPos spawnPos = SpawnPoint.getApproximateSpawn((OverworldBiomeSource) obs).toChunkPos();
            if (spawnPos.distanceTo(vPos, DistanceMetric.CHEBYSHEV) > 8) continue;

            try {
                System.out.println("writing seed to file");
                fw = new FileWriter(fileName, true);
                bw = new BufferedWriter(fw);
                bw.write(Long.toString(worldSeed));
                bw.newLine();
                bw.close();
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
