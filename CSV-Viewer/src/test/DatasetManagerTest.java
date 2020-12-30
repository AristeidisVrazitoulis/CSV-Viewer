package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import server.DatasetManager;

public class DatasetManagerTest {
	private DatasetManager manager;
	@Before
	public void setUp() {
		manager=new DatasetManager();
	}
	@Test
	public final void testEmptyNameDataset() {
		assertEquals("test if register dataset allows to give empty name",-1,manager.registerDataset("", ".\\src\\test\\NBA_team_stats.csv"));
	}
	@Test
	public final void testExistingNameDataset() {
		manager.registerDataset("name", ".\\src\\test\\NBA_team_stats.csv");
		assertEquals("test if register dataset allows to create dataset with existing name",-10,manager.registerDataset("name", ".\\src\\test\\NBA_team_stats.csv"));
	}
	@Test
	public final void testRegisterDataset() {
		assertEquals("test if register dataset allows to create dataset with existing name",0,manager.registerDataset("name", ".\\src\\test\\NBA_team_stats.csv"));
	}
	@Test
	public final void testRetrieveNotExistingDataset() {
		assertNull("Dataset does Not exist",manager.retrieveDataset("random ", new ArrayList<String[]> ()));
	}
	@Test
	public final void testRetrieveExistingDataset() {
		manager.registerDataset("name", ".\\src\\test\\NBA_team_stats.csv");
		assertNotNull("Check if retrieve works ok ",manager.retrieveDataset("name", new ArrayList<String[]> ()));
	}
	@Test
	public final void testFilterNotExistingDataset() {
		assertEquals("test filter not existing dataset ",-1,manager.filterDataset("random", "new name", "filterColumnName", "filterValue"));
	}
	@Test
	public final void testFilterNotExistingAttribute() {
		manager.registerDataset("name",".\\src\\test\\NBA_team_stats.csv" );
		assertEquals("test filter not existing attribute ",-1,manager.filterDataset("name", "new name", "filterColumnName", "filterValue"));
	}
	@Test
	public final void testFilterDataset() {
		manager.registerDataset("name",".\\src\\test\\NBA_team_stats.csv" );
		assertEquals("test filter with existing dataset ",0,manager.filterDataset("name", "new name", "Team", "Boston Celtics"));
	}
	@Test
	public final void testgetDatasetProjectionNotExistingDataset() {
		assertNull("test get dataset projection ",manager.getDatasetProjection("name", new ArrayList<String>()));
	}
	@Test
	public final void testgetDatasetProjectionNotExistingAttribute() {
		manager.registerDataset("name",".\\src\\test\\NBA_team_stats.csv" );
		ArrayList<String> attributes=new ArrayList<String>();
		attributes.add("random");
		attributes.add("random");
		assertNull("test get dataset projection ",manager.getDatasetProjection("name", attributes));
	}
	@Test
	public final void testgetDatasetProjection() {
		manager.registerDataset("name",".\\src\\test\\NBA_team_stats.csv" );
		ArrayList<String> attributes=new ArrayList<String>();
		attributes.add("PTS");
		attributes.add("W");
		assertNotNull("test get dataset projection ",manager.getDatasetProjection("name", attributes));
	}
//	public final void test
}
