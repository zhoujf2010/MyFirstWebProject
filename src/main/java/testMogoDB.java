import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class testMogoDB
{

    public static void main(String[] args) {

        String IP = "192.168.201.129";
        int port = 27017;
        String dbname = "test";

        MongoClient mg = new MongoClient(IP, port);
        MongoDatabase db = mg.getDatabase(dbname);
        MongoCollection<Document> users = db.getCollection("frame_user");

        // 插入
        String json = "{'userguid' : '1','displayname' : '张三'," + "'detail' : {'age' : 99, 'address' : '张家港'}}";
        Document dbObject = Document.parse(json);

        users.insertOne(dbObject);

        json = "{'userguid' : '2','displayname' : '李四'," + "'detail' : {'age' : 99, 'address' : '北京',isenable:1}}";
        dbObject = Document.parse(json);
        users.insertOne(dbObject);

        // 读取
        for (Document item : users.find().sort(new BasicDBObject("displayname", 1))) {
            System.out.println(item.get("displayname") + "  " + ((Document) item.get("detail")).getString("address"));
        }

        // 更新，修改
        // users.updateOne(arg0, arg1);

        // 删除
        // users.deleteOne(arg0);

        mg.close();

    }

}
