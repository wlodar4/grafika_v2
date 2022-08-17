package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		
		
		
		RawModel model = OBJLoader.loadObjModel("tree", loader);
		
		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for(int i=0;i<500;i++){
			entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
		}
		
		Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
		
		//Original
		//Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass")));
		//Terrain terrain2 = new Terrain(1,0,loader,new ModelTexture(loader.loadTexture("grass")));
		//
		Terrain terrain = new Terrain(0,-1,loader,texturePack, blendMap, "heightmap");
		//Terrain terrain2 = new Terrain(-1,-1,loader,texturePack, blendMap, "heightmap");
		
		
		MasterRenderer renderer = new MasterRenderer();
		
		RawModel bunnyModel = OBJLoader.loadObjModel("person", loader);
		TexturedModel person = new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("white")));
		
		Player player = new Player(person, new Vector3f(100, 0 , -50),0,0,0,1);
		Camera camera = new Camera(player);	
		
		while(!Display.isCloseRequested()){
			camera.move();
			player.move(terrain);
			
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			//renderer.processTerrain(terrain2);
			for(Entity entity:entities){
				renderer.processEntity(entity);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}

		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
