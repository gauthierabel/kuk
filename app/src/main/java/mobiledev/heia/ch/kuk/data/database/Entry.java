package mobiledev.heia.ch.kuk.data.database;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@SuppressWarnings("unused")
@Entity(tableName = "recently_viewed", indices = {@Index(value = {"id"}, unique = true),
    @Index(value = {"uid"}, unique = true)})
public class Entry implements Serializable {
  // Data members/fields
  // each data member/field represents a field in each piece of data
  // received from the web API

  // Autogenerate ID (useful for history management in database)
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "uid")
  private int uID;

  // ID and name of recipe in API
  @ColumnInfo(name = "id")
  private int recipeID;
  @ColumnInfo(name = "name")
  private String recipeName;

  // Description
  @ColumnInfo(name = "category")
  private String recipeCategory;
  @ColumnInfo(name = "area")
  private String recipeArea;
  @ColumnInfo(name = "tags")
  private String[] recipeTags;

  // Media resources
  @ColumnInfo(name = "img_url")
  private String recipeThumbnail;
  @ColumnInfo(name = "youtube")
  private String recipeYouTube;

  // Recipe contents
  @ColumnInfo(name = "instructions")
  private String recipeInstructions;
  @ColumnInfo(name = "ingredients")
  private String[] recipeIngredients;
  @ColumnInfo(name = "measures")
  private String[] recipeMeasures;

  @ColumnInfo(name = "img")
  private transient Bitmap img;

  // Constructor
  // each data field must be initialized correctly upon construction
  // based on data received from the web API
  public Entry() {
  }

  public Entry(
      int recipeID, String recipeName, String recipeCategory, String recipeArea, String[] recipeTags,
      String recipeThumbnail, String recipeYouTube,
      String recipeInstructions, String[] recipeIngredients, String[] recipeMeasures, Bitmap img) {
    this.recipeID = recipeID;
    this.recipeName = recipeName;
    this.recipeCategory = recipeCategory;
    this.recipeArea = recipeArea;
    this.recipeTags = recipeTags;
    this.recipeThumbnail = recipeThumbnail;
    this.recipeYouTube = recipeYouTube;
    this.recipeInstructions = recipeInstructions;
    this.recipeIngredients = recipeIngredients;
    this.recipeMeasures = recipeMeasures;
    this.img = img;
  }

  // Accessor methods used for accessing specific data fields

  public int getUID() {return uID;}
  public void setUID(int uID) {this.uID = uID;}

  public int getRecipeID() {return recipeID;}
  public void setRecipeID(int recipeID) {this.recipeID = recipeID;}

  public String getRecipeName() {return recipeName;}
  public void setRecipeName(String recipeName) {this.recipeName = recipeName;}

  public String getRecipeCategory() {return recipeCategory;}
  public void setRecipeCategory(String recipeCategory) {this.recipeCategory = recipeCategory;}

  public String getRecipeArea() {return recipeArea;}
  public void setRecipeArea(String recipeArea) {this.recipeArea = recipeArea;}

  public String[] getRecipeTags() {return recipeTags; }
  public void setRecipeTags(String[] recipeTags) {this.recipeTags = recipeTags;}

  public String getRecipeThumbnail() {return recipeThumbnail;}
  public void setRecipeThumbnail(String recipeThumbnail) {this.recipeThumbnail = recipeThumbnail;}

  public String getRecipeYouTube() {return recipeYouTube;}
  public void setRecipeYouTube(String recipeYouTube) {this.recipeYouTube = recipeYouTube;}

  public String getRecipeInstructions() {return recipeInstructions;}
  public void setRecipeInstructions(String recipeInstructions) {
    this.recipeInstructions = recipeInstructions;
  }

  public String[] getRecipeIngredients() {return recipeIngredients;}
  public void setRecipeIngredients(String[] recipeIngredients) {
    this.recipeIngredients = recipeIngredients;
  }

  public String[] getRecipeMeasures() {return recipeMeasures;}
  public void setRecipeMeasures(String[] recipeMeasures) {this.recipeMeasures = recipeMeasures;}

  public Bitmap getImg() {return img;}
  public void setImg(Bitmap img){this.img = img;}
}