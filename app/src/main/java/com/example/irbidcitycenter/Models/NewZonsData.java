package com.example.irbidcitycenter.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "NewZonsDataTABLE")
public class NewZonsData {

   @PrimaryKey(autoGenerate = true)
   public int SERIAL;

   @ColumnInfo(name = "ZONENO")
   String ZONENO;
   @ColumnInfo(name = "ITEMCODE")
   String ITEMCODE;
   @ColumnInfo(name = "QTY")
   String QTY;

   public int getSERIAL() {
      return this.SERIAL;
   }

   public void setSERIAL(final int SERIAL) {
      this.SERIAL = SERIAL;
   }

   public String getZONENO() {
      return this.ZONENO;
   }

   public void setZONENO(final String ZONENO) {
      this.ZONENO = ZONENO;
   }

   public String getITEMCODE() {
      return this.ITEMCODE;
   }

   public void setITEMCODE(final String ITEMCODE) {
      this.ITEMCODE = ITEMCODE;
   }

   public String getQTY() {
      return this.QTY;
   }

   public void setQTY(final String QTY) {
      this.QTY = QTY;
   }
}
