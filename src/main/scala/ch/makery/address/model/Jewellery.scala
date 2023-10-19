package ch.makery.address.model
import scalafx.beans.property.{StringProperty, DoubleProperty, IntegerProperty, ObjectProperty}
import ch.makery.address.util.Database
import scalikejdbc._
import scala.util.{ Try, Success, Failure }

class Jewellery (val jewelleryName : String, val jewelleryType : String) extends Database {

    def this() = this(null, null)
    var name = new StringProperty(jewelleryName)
    var jType = new StringProperty(jewelleryType)
    var jPrice = ObjectProperty[Double](0.00)
    var company = new StringProperty("companyName")
    var material = new StringProperty("silver")
    var quantity = ObjectProperty[Int](1)

    def save() : Try[Int] = {
        if (!(isExist)) {
        Try(DB autoCommit { implicit session =>
            sql"""
            insert into jewellery (name, jType,
            jPrice, company, material, quantity) values
            (${name.value}, ${jType.value}, ${jPrice.value},
            ${company.value}, ${material.value}, ${quantity.value})
            """.update.apply()
        })
    } else {
        Try(DB autoCommit { implicit session =>
            sql"""
            update jewellery
            set
            name = ${name.value} ,
            jType = ${jType.value},
            jPrice = ${jPrice.value},
            company = ${company.value},
            material = ${material.value} and
            quantity = ${quantity.value} 
            """.update.apply()
            })
        }
    }

    def delete() : Try[Int] = {
        if (isExist) {
        Try(DB autoCommit { implicit session =>
        sql"""
        delete from jewellery where
        name = ${name.value} and jType = ${jType.value}
        """.update.apply()
        })
        } else
        throw new Exception("Jewellery does not exist in database")
    }

    def isExist : Boolean = {
        DB readOnly { implicit session =>
        sql"""
        select * from jewellery where
        name = ${name.value} and jType = ${jType.value}
        """.map(rs => rs.string("name")).single.apply()
        } match {
        case Some(x) => true // if it's there then return true
        case None => false
        }
    }
}

object Jewellery extends Database{
    def apply (
        nameS : String,
        jTypeS : String,
        jPriceD : Double,
        companyS : String,
        materialS : String,
        quantityI : Int
        ) : Jewellery = {

        new Jewellery(nameS, jTypeS) {
        jPrice.value = jPriceD
        company.value = companyS
        material.value = materialS
        quantity.value = quantityI
        }
        
    }
    def initializeTable() = {
        DB autoCommit { implicit session =>
            sql"""
            create table jewellery (
            id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
            name varchar(64),
            jType varchar(64),
            jPrice Double,
            company varchar(200),
            material varchar(64),
            quantity int
            )
            """.execute.apply()
        }
    }
    def getAllJewelleries : List[Jewellery] = {
        DB readOnly { implicit session =>
            sql"select * from jewellery".map(rs => Jewellery(rs.string("name"),
            rs.string("jType"),rs.double("jPrice"),rs.string("company"),rs.string("material"),rs.int("quantity") )).list.apply()
        }
    }
}