import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestSQLite{

	void createDatabase(Connection connection,String tableName,String sql) throws SQLException{
		Statement statement= connection.createStatement();
		sql="create table "+tableName+"(\n"+sql+");";
	
		statement.execute(sql);
	}
	
	void updateTable(Connection connection,String tableName,int id,String name) throws SQLException{
		Statement statement= connection.createStatement();
		String sql="update "+tableName+" set name = '"+name+"' where id="+id+";";
	
		statement.execute(sql);
	}
	
	void deleteTable(Connection connection,String tableName,int id) throws SQLException{
		Statement statement= connection.createStatement();
		String sql="delete from "+tableName+" where id="+id+";";
	
		statement.execute(sql);
	}
	
	void dropTable(Connection connection,String tableName) throws SQLException{
		Statement statement= connection.createStatement();
		String sql="drop table "+tableName+";";

		statement.execute(sql);
	}
	
	void insertIntoTable(Connection connection,String tableName,int id,String name) throws SQLException{
		Statement statement= connection.createStatement();
		String sql="insert into "+tableName+"(id,name) values ("+id+",'"+name+"');";
		
		//System.out.println(sql);
		statement.execute(sql);
	}
	
	void customStatement(Connection connection,String sql) throws SQLException{
		Statement statement= connection.createStatement();

		statement.execute(sql);
	}
	
	ResultSet customQuery(Connection connection,String sql) throws SQLException{
		Statement statement= connection.createStatement();
		ResultSet resultSet=statement.executeQuery(sql);
		return resultSet;
	}
	
	void selectAllFromTable(Connection connection,String tableName) throws SQLException {
    	Statement statement = connection.createStatement();
    	String sql="select * from "+tableName+";";
    	
        ResultSet resultSet=statement.executeQuery(sql);
        
        while (resultSet.next())
            	System.out.println(resultSet.getInt(1)+","+resultSet.getString(2));
        System.out.print("\n");
	}


    
    public static void main(String[] args) throws SQLException, IOException {

        Connection connection=DriverManager.getConnection("jdbc:sqlite:test.db"); 

        String sql1="id integer primary key not null,\nname text not null";
        
        String sql2="id integer primary key not null,\nname text not null";
        
        String sql3="id integer primary key not null,\n"+
        			"name text not null,\n"+
        			"id_aaa integer not null,\n"+
        			"id_bbb integer not null,\n"+
        			"foreign key(id_aaa) references AAA(id),\n"+
        			"foreign key(id_bbb) references BBB(id)";
        
        TestSQLite test=new TestSQLite();
        String tableName1="AAA";
        String tableName2="BBB";
        String tableName3="CCC";
        
        test.dropTable(connection,tableName1);
        test.createDatabase(connection,tableName1,sql1);
        
        test.dropTable(connection,tableName2);
        test.createDatabase(connection,tableName2,sql2);
        
        test.dropTable(connection,tableName3);
        test.createDatabase(connection,tableName3,sql3);

        test.insertIntoTable(connection,tableName1,1,"aPol");
        test.insertIntoTable(connection,tableName1,3,"ccccc");
        test.insertIntoTable(connection,tableName1,2,"bbb");
        
        test.insertIntoTable(connection,tableName2,1,"bb");
        test.insertIntoTable(connection,tableName2,2,"bPol");
        test.insertIntoTable(connection,tableName2,3,"bPol2");
        test.insertIntoTable(connection,tableName2,4,"bPol3");
        test.insertIntoTable(connection,tableName2,5,"xxxx");
        
        test.customStatement(connection,"insert into CCC(id,name,id_aaa,id_bbb) VALUES(1,'ccccp',1,2)");
        test.customStatement(connection,"insert into CCC(id,name,id_aaa,id_bbb) VALUES(2,'ccss',1,3)");
        test.customStatement(connection,"insert into CCC(id,name,id_aaa,id_bbb) VALUES(3,'ccccscp',1,4)");
        test.customStatement(connection,"insert into CCC(id,name,id_aaa,id_bbb) VALUES(4,'cccduplikat',1,3)");
        
        //test.selectAllFromTable(connection,tableName1);
        //test.selectAllFromTable(connection,tableName2);
        //test.selectAllFromTable(connection,tableName3);
        
        ResultSet rs2=test.customQuery(connection,"select AAA.NAME,BBB.NAME from AAA,BBB,CCC where "
        								+"(AAA.id=CCC.id_aaa)AND(CCC.id_bbb=BBB.id);");

        while(rs2.next())System.out.print(rs2.getString(1)+","+rs2.getString(2)+"\n");
        
        
        test.updateTable(connection,tableName1,1,"noweee");
        test.deleteTable(connection,tableName2,3);
        
        test.selectAllFromTable(connection,tableName1);
        test.selectAllFromTable(connection,tableName2);
        test.selectAllFromTable(connection,tableName3);
        
        
    }
}

