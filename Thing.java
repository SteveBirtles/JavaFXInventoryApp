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
                try {								// ...add each one to the list.
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

}
