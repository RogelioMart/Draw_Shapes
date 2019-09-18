// Assignment from a previous java class
//  Description: The DrawPane class creates a canvas where we can use
//               mouse key to draw either a Rectangle or a Circle with different
//               colors. We can also use the the two buttons to erase the last
//				 drawn shape or clear them all.

//all necessary classes are imported
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;


import java.util.ArrayList;

public class DrawPane extends BorderPane
{
   private Button undoBtn, eraseBtn;
   private ComboBox<String> colorCombo;
   private RadioButton rbRect, rbCircle;
   private ArrayList<Shape> shapeList;
   private Pane canvas;

   Rectangle prism; //declares the Rectangle object
   Circle ring; //declares the circle object
   
   double x; //the starting point in the x axis for the rectangle
   double y; //the starting point in the y axis for the rectangle
   double w; //the width of the rectangle.
   double h; //the height of the rectangle
   double x2;//to determine where the mouse dragged to to before releasing it in the x axis
   double y2;//to determine where the mouse dragged to to before releasing it in the y axis
   double radius; //used to store the radius of the circle and make the circle object
   double temp; //temporary variable used in making the rectangle object
   double tempx; //a temporary variable used to calculate the radius of the Circle (specifically used for x)
   double tempy; //a temporary variable used to calculate the radius of the circle (specifically used for y)
   boolean radio; //used to know which radio button is selected
   int total = -1; //used to keep count of how many elements are in ShapeList
   public Color colour; //used to change the color of the shapes with the help of
   String border = "-fx-border-color: Black;"; //string border to give the vbox and hbox a border

   //Constructor
   public DrawPane()
   {//begins the constructor DrawPane
	   
      //Step #1: initialize each instance variable and set up layout
      undoBtn = new Button("Undo");
      eraseBtn = new Button("Erase");
      undoBtn.setMinWidth(80.0);
      eraseBtn.setMinWidth(80.0);

      
      colorCombo = new ComboBox<String>(); //initializes the ComboBox colorCombo 
      colorCombo.getItems().addAll("Black", "Blue", "Green", "Red", "Yellow", "Orange", "Pink");//declares the item that can be selected in colorBox
      colorCombo.getSelectionModel().select("Black"); //sets the default selection for the ComboBox
      colour = Color.BLACK;

      radio = true; //initializes the radio variables
      rbRect = new RadioButton("Rectangle"); //initializes the radio button rbRect
      rbCircle = new RadioButton("Circle"); //initializes the radio button rbCircle
      final ToggleGroup group = new ToggleGroup(); //creates the Toggle Group for the radio buttons
      rbCircle.setToggleGroup(group); //puts rbCircle into the group
      rbRect.setSelected(true); //sets rbRect equal to true
      rbRect.setToggleGroup(group); //puts rbRect into the group 
      
      shapeList = new ArrayList<Shape>();//initializes the shapeList data structure

      canvas = new Pane(); //initializes the canvas Pane
      canvas.setStyle("-fx-background-color: beige;"); //sets the color background of the canvas to beige
      
      VBox vbox = new VBox(); //declares and initializes vbox
      HBox hbox = new HBox(); //declares and initializes hbox
      vbox.setPadding(new Insets(20,20,20,20)); //sets padding for the vbox
      hbox.setPadding(new Insets(20,20,20,20)); //sets padding for the hbox
      vbox.setSpacing(70); //sets the spacing inside the vbox
      hbox.setSpacing(8); //sets the spacing inside the hbox
      vbox.setStyle(border);//sets a border for vbox
      hbox.setStyle(border);//sets a border for hbox
      hbox.setAlignment(Pos.CENTER); //aligns the elements in the hbox to the center (just the buttons erase and undo)
      vbox.getChildren().addAll(colorCombo, rbRect, rbCircle); //gets the elements needed for the vbox
      hbox.getChildren().addAll(undoBtn, eraseBtn); //gets the elements needed for the hbox
      setLeft(vbox); //sets the vbox to the left of the window
      setCenter(canvas); //sets the canvas to the center (it really ends up filling up the space in the top, right, and center)
      setBottom(hbox); //sets the hbox to the bottom of the window
      
      //Registers the source nodes with their appropriate handler objects
      canvas.setOnMouseEntered(new MouseHandler()); //used for the mouse events
      ButtonHandler ButtonHandler = new ButtonHandler(); //used for the button handlers
      eraseBtn.setOnAction(ButtonHandler); //used to connect eraseBtn to the Handler
      undoBtn.setOnAction(ButtonHandler); //used to connect undoBtn to the Handler
      rbRect.setOnAction(new ShapeHandler()); //used to connect the Radio button rbRect with its Handler
      rbCircle.setOnAction(new ShapeHandler()); //used to connect the Radio button rbCircle with its Handler
      colorCombo.setOnAction(new ColorHandler()); //used for the BomboBox Handler


    }//ends the constructor DrawPane
  
   
   private class MouseHandler implements EventHandler<MouseEvent>
   {//opens MouseHandler
	   
      public void handle(MouseEvent eventM)
      {//opens handle eventM
    	  
/////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////setOnMousePressed/////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
		 
    	 canvas.setOnMousePressed(new EventHandler<MouseEvent>() 
    	 {//opens the Mouse event Handler for pressed
    		 
    		 public void handle (MouseEvent eventM)
    		 {//opens the handle
    			 
    			
    			 if(radio == true) //the rectangle is chosen
    			 {//opens if(radio == true)
    				 
    				 //get the first coordinates for the rectangle
    				 x = eventM.getX();
    				 y = eventM.getY();
    				     				 
    				 prism = new Rectangle();//initializes the rectangle
    				 
    				 //sets the origin points for the rectangle
    				 prism.setX(x);
    				 prism.setY(y);
    				 
    				 //sets up the color of the border and rectangle
    				 prism.setFill(Color.WHITE);
    				 prism.setStroke(Color.BLACK);
    				 
    				 canvas.getChildren().add(prism);//displays the rectangle on the canvas
    				 
    			 }//closes if(radio == true)
    			 
    			 else if (radio == false) //the circle is chosen
    			 {// opens if (radio == false)
    				 
    				 //gets the initial coordinates for the circle
    				 x = eventM.getX();
    				 y = eventM.getY();
    				 
    				 ring = new Circle();//initializes the circle object
    				 
    				 //sets up the center of the circle
    				 ring.setCenterX(x);
    				 ring.setCenterY(y);
    				 
    				 //sets up the coloring for the shape
    				 ring.setFill(Color.WHITE);
    				 ring.setStroke(Color.BLACK);
    				 
    				 canvas.getChildren().add(ring);//displays the circle on the canvas
    				 
    			 }//closes if (radio == false)
    			 
    		 }//closes the handle
    		 
    	 }//closes the Mouse event Handler for pressed
    	 );//closes the setOnMousePressed action

/////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////setOnMouseDragged/////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
    	 
    	 canvas.setOnMouseDragged(new EventHandler<MouseEvent>()
    	 {//opens the mouse vent handler for Dragged
    		     
    		 public void handle (MouseEvent eventM) 
    		 {//opens the handle 
    			 
    			 if(radio == true)//Rectangle is chosen
    			 {//opens if(radio == true)
    				 
    				 //get the last values where the rectangle coordinates
	    			 x2 = eventM.getX();
	    			 y2 = eventM.getY();
	    			 
	    			 //calculate the width and the height for the rectangle
		    	 	 w = Math.abs(x2 - x);
					 h = Math.abs(y2 - y);
	    			 
	    			//the following 2 if statements are used to keep the rectangle inside the canvas pane
					if(canvas.getWidth() < (x + w))
						w = canvas.getWidth() - x;
												
					if (canvas.getHeight() < (y + h))
						h = canvas.getHeight() - y;
	    	 		 
					//set the width and height for the rectangle
	    	 		 prism.setWidth(w);
	    	 		 prism.setHeight(h);
	    	 		
    			 }//closes if(radio == true)
    			 
    			 else if(radio == false) //the circle is chosen
    			 {//opens else if statement (radio == false) 
    				 
    				 //temporary variables used to later calculate the proper radius of the circle
    				 tempx = eventM.getX();
    				 tempy = eventM.getY();
    				 
    				 x2 = Math.abs(tempx - x);//calculates the x portion of the radius equation
    				 y2 = Math.abs(tempy - y);//Calculates the y portion of the radius equation
    				 radius = Math.sqrt((x2 * x2)+(y2 * y2));//calculates the radius
    				 
    				 //These 4 if statements are used to keep the circle within the confines of the canvas
    				 if (radius > ((canvas.getWidth()) - x))
     					radius = (canvas.getWidth()) - x;
     					
     				if (radius > (canvas.getHeight()) - y)
     					radius = (canvas.getHeight()) - y;
     					
     				if (radius > x)
     					radius = x;

     				if (radius > y)
     					radius = y;
    				
    				 ring.setRadius(radius);//sets the radius for the circle
    				     				 
    			 }//closes else if statement (radio == false)
    	 		
    		 }//closes the handle 
    		 
    	 }//closes the mouse event handler for Dragged
    	 );//closes setOnMouseDragged
    	 
/////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////setOnMouseReleased/////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
    	 
    	 canvas.setOnMouseReleased(new EventHandler<MouseEvent>() 
    	 {//opens the mouse event Handler for released
    		 
    		 public void handle(MouseEvent eventM)
    		 {//opens the handle
    			 
    			 if (radio == true)//rectangle is chosen
    			 {//opens if (radio == true)
    			    	 		 
	    			 prism.setFill(colour);//changes the color of the rectangle from white to the selected color
	   			 
	    			 shapeList.add(prism);//adds the rectangle to shapeList
	    			 total++;//updates the total number of items in shapeList
    			 
    			 }//ends if (radio == true)
    			 
    			 else if(radio == false)//circle is chosen
    			 {//opens the else if statement for (radio == false)
    				     			
    				 ring.setFill(colour);//changes the color of the circle from white to the selected color 
        			 
        			 shapeList.add(ring);//adds the new shape to shapeList
        			 total++; //updates the total number of items in shapeList
    				 
    				 
    			 }//closes the else if statement for (radio == false)
    			 
    		 }//closes the handle
    		 
    	 }//closes the mouse event Handler for released
    	 );//closes setOnMouseReleased


      }//end handle()
   }//end MouseHandler

   //Step #2(B)- A handler class used to handle events from Undo & Erase buttons
   private class ButtonHandler implements EventHandler<ActionEvent>
   {//opens ButtonHandler
      public void handle(ActionEvent eventB)
      {//opens eventB for button handler
		  
    	  Object source = eventB.getSource(); //gets the source of what button was pushed
    	  
    	  if(source == eraseBtn)//clears the canvas
    	  {//opens if(source == eraseBtn)
    		  
    		  if(total != -1)
    		  {//opens the if statement for clear
    			  
    			  //clears the canvas and updates the total number of elements
	    		  canvas.getChildren().clear();
	    		  total = -1;
	    		  shapeList.clear();
	    		  
	    		  
    		  }//closes the if statement for clear
    		  
    	  }//closes if(source == eraseBtn)
    	  
    	  else if (source == undoBtn) //removes the last shape that was drawn
    	  {//opens else if (source == undoBtn)
    		    		  
    		  if (total != -1)
    		  {//opens the if statement for removing the last shape from canvas
    			  
    			  //removes the last shape drawn and updates the total number of elements in shapeList
	    		  shapeList.remove(total);
	    		  canvas.getChildren().clear();
	    		  total--;
	    		  canvas.getChildren().addAll(shapeList);
	    		  
    		  }//closes the if statement for removing the last shape from canvas
    		  
    	  }//closes else if (source == undoBtn)
    	  

      }//closes eventB for button handler
   }//ends ButtonHandler

   //Step #2(C)- A handler class used to handle events from the two radio buttons
   private class ShapeHandler implements EventHandler<ActionEvent>
   {//opens ShapeHandler
      public void handle(ActionEvent eventS)
      {//opens the ShapeHandler for eventS
    	  
		  //if statements for the radio buttons, to check which one is selected
    	 if (rbCircle == eventS.getSource())//if selected the circle will be drawn
    	 {
    		 radio = false;
    		 
    	 }
    	 else if(rbRect == eventS.getSource())//if selected the rectangle will be drawn
    	 {
    		 
    		 radio = true;
    		 
    	 }



      }//closes the ShapeHandler for eventS
   }//end ShapeHandler

   //Step #2(D)- A handler class used to handle colors from the combo box
   private class ColorHandler implements EventHandler<ActionEvent>
   {//begins the ColorHandler
      public void handle(ActionEvent eventC)
      {//begins ColorHandler for EventC
    	  
		  //the following if statements will change the color of the shape depending on what is selected in the ComboBox
    	  
    	  if(colorCombo.getValue().equals("Black"))//if Black is selected colour will be set to BLACK
    	  {
    		  colour= Color.BLACK; 
    	  }
    	  else if(colorCombo.getValue().equals("Blue"))//if Blue is selected colour will be set to BLUE
    	  {
    		  colour = Color.BLUE;
    	  }
    	  else if(colorCombo.getValue().equals("Green"))//if Green is selected colour will be set to GREEN
    	  {
    		  colour = Color.GREEN;
    	  }
    	  else if(colorCombo.getValue().equals("Red"))//if Red is selected colour will be set to RED
    	  {
    		  colour = Color.RED;
    	  }
    	  else if(colorCombo.getValue().equals("Yellow"))//if Yellow is selected colour will be set to YELLOW
    	  {
    		  colour = Color.YELLOW;
    	  }
    	  else if(colorCombo.getValue().equals("Orange"))//if Orange is selected colour will be set to ORANGE
    	  {
    		  colour = Color.ORANGE;
    	  }
    	  else if(colorCombo.getValue().equals("Pink"))//if Pink is selected colour will be set to PINK
    	  {
    		  colour = Color.PINK;
    	  }
    		  
      }//ends ColorHandler for EventC
   }//end ColorHandler

}//end class DrawPane