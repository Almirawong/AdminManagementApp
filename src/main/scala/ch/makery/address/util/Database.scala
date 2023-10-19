package ch.makery.address.util
import scalikejdbc._
import ch.makery.address.model.Jewellery

trait Database {
val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"
val dbURL = "jdbc:derby:myDB;create=true;"; // create a database if you don't have it, otherwise just go to the database 

// initialize JDBC driver & connection pool
Class.forName(derbyDriverClassname)

ConnectionPool.singleton(dbURL, "me", "mine") // username: me, password: mine

// ad-hoc session provider on the REPL
implicit val session = AutoSession
}

object Database extends Database{
    def setupDB() = {
        if (!hasDBInitialize)
            Jewellery.initializeTable()
    }
    
    def hasDBInitialize : Boolean = {
        DB getTable "Jewellery" match {
            case Some(x) => true
            case None => false
        }
    }
}

