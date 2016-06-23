import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;

/* Each table you wish to access in your database requires a model class, like this example: */
public class Thing
{
    /* First, map each of the fields (columns) in your table to some public variables. */
    public int id;
    public String name;
    public int categoryId
    ;
    /* Next, prepare a constructor that takes each of the fields as arguements. */
    public Thing(int id, String name, int categoryId)
    {
        this.id = id;        
        this.name = name;
        this.categoryId = categoryId;
    }

    /* A toString method is vital so that your model items can be sensibly displayed as text. */
    @Override public String toString()
    {
        return name;
    }

    /* Different models will require different read and write methods. Here is an example 'loadAll' method 
     * which is passed the target list object to populate. */
    public static void readAll(List<Thing> list)
    {
        list.clear();       // Clear the target list first.

        /* Create a new prepared statement object with the desired SQL query. */
        PreparedStatement statement = Application.database.newStatement("SELECT id, name, categoryId FROM things ORDER BY id"); 

        if (statement != null)      // Assuming the statement correctly initated...
        {
            ResultSet results = Application.database.runQuery(statement);       // ...run the query!

            if (results != null)        // If some results are returned from the query...
            {
                try {                               // ...add each one to the list.
                    while (results.next()) {                                               
                        list.add( new Thing(results.getInt("id"), results.getString("name"), results.getInt("categoryId")));
                    }
                }
                catch (SQLException resultsexception)       // Catch any error processing the results.
                {
                    System.out.println("Database result processing error: " + resultsexception.getMessage());
                }
            }
        }

    }

    public static Thing getById(int id)
    {
        Thing thing = null;

        PreparedStatement statement = Application.database.newStatement("SELECT id, name, categoryId FROM things WHERE id = ?"); 

        try 
        {
            if (statement != null)
            {
                statement.setInt(1, id);
                ResultSet results = Application.database.runQuery(statement);

                if (results != null)
                {
                    thing = new Thing(results.getInt("id"), results.getString("name"), results.getInt("categoryId"));
                }
            }
        }
        catch (SQLException resultsexception)
        {
            System.out.println("Database result processing error: " + resultsexception.getMessage());
        }

        return thing;
    }

    public static void deleteById(int id)
    {
        try 
        {

            PreparedStatement statement = Application.database.newStatement("DELETE FROM things WHERE id = ?");             
            statement.setInt(1, id);

            if (statement != null)
            {
                Application.database.executeUpdate(statement);
            }
        }
        catch (SQLException resultsexception)
        {
            System.out.println("Database result processing error: " + resultsexception.getMessage());
        }

    }

    public void save()    
    {
        PreparedStatement statement;

        try 
        {

            if (id == 0)
            {

                statement = Application.database.newStatement("SELECT id FROM things ORDER BY id DESC");             

                if (statement != null)	
                {
                    ResultSet results = Application.database.runQuery(statement);
                    if (results != null)
                    {
                        id = results.getInt("id") + 1;
                    }
                }

                statement = Application.database.newStatement("INSERT INTO things (id, name, categoryId) VALUES (?, ?, ?)");             
                statement.setInt(1, id);
                statement.setString(2, name);
                statement.setInt(3, categoryId);         

            }
            else
            {
                statement = Application.database.newStatement("UPDATE things SET name = ?, categoryId = ? WHERE id = ?");             
                statement.setString(1, name);
                statement.setInt(2, categoryId);   
                statement.setInt(3, id);
            }

            if (statement != null)
            {
                Application.database.executeUpdate(statement);
            }
        }
        catch (SQLException resultsexception)
        {
            System.out.println("Database result processing error: " + resultsexception.getMessage());
        }

    }

}
