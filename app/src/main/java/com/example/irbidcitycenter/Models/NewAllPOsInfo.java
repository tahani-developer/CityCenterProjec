package com.example.irbidcitycenter.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "NewAllPOsInfoTABLE")
public class NewAllPOsInfo {

   @PrimaryKey(autoGenerate = true)
   public int SERIAL;

   @ColumnInfo(name = "PONO")
   String PONO;
   @ColumnInfo(name = "ITEMCODE")
   String ITEMCODE;
   @ColumnInfo(name = "QTY")
   String QTY;
   @ColumnInfo(name = "BOXNO")
   String BOXNO;

   public int getSERIAL() {
      return this.SERIAL;
   }

   public void setSERIAL(final int SERIAL) {
      this.SERIAL = SERIAL;
   }

   public String getPONO() {
      return this.PONO;
   }

   public void setPONO(final String PONO) {
      this.PONO = PONO;
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

   public String getBOXNO() {
      return this.BOXNO;
   }

   public void setBOXNO(final String BOXNO) {
      this.BOXNO = BOXNO;
   }
}
