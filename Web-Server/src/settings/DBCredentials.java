package settings;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.jongo.Jongo;

public class DBCredentials {
	private String dbName;
	private static MongoClient mongoClient;
	private static MongoDatabase mongoDatabase;
	private static Jongo jongo;

	public Jongo createJongo() {
		mongoClient = new MongoClient();
		mongoDatabase = mongoClient.getDatabase(dbName);
		jongo = new Jongo(mongoClient.getDB(dbName));

		return jongo;
	}

	public String getDbName() {
		return dbName;
	}
}
