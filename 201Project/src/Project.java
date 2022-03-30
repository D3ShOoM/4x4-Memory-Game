import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

//*************************************************************************
//	Hosam Almutawah	 ID:201837260  	sec:04
//
//	note: wait for the animation to end before clicking.
//
//*************************************************************************
public class Project extends Application {

	private ArrayList<ImageView> image = new ArrayList<>();
	private ArrayList<Card> cards = new ArrayList<>();
	private Text winner = new Text(""),					// winner text to show after winning
			top1 = new Text(),							// top1 text
			top2 = new Text(),							// top2 text
			top3 = new Text();							// top3 text
	private int secs, mins;								// for the timer
	private int topS1,topS2,topS3;						// top3 wins times in seconds
	private Card selected = null;						// for the already selected card
	private int score;									// every time the images are matched  score++;  to check of win condition
	private Button start;								// to start and reset the game
	private Timeline t;									// for the timer
	private MediaPlayer correctCard, wrongCard, win;	// for the sounds
	
	@Override
	public void start(Stage ps) throws Exception{
		//creating the images and sounds 
		Image apple = new Image("file:images/Apple.jpg");						
		Image bannana = new Image("file:images/Banana.jpg");
		Image cherry = new Image("file:images/Cherry.jpg");
		Image lemon = new Image("file:images/Lemon.jpg");
		Image melon = new Image("file:images/Melon.jpg");
		Image orange = new Image("file:images/Orange.jpg");
		Image pineapple = new Image("file:images/Pineapple.jpg");
		Image strawberry = new Image("file:images/Strawberry.jpg");
		
		image.add(new ImageView(apple));
		image.add(new ImageView(bannana));
		image.add(new ImageView(cherry));
		image.add(new ImageView(lemon));
		image.add(new ImageView(melon));
		image.add(new ImageView(orange));
		image.add(new ImageView(pineapple));
		image.add(new ImageView(strawberry));

		image.add(new ImageView(apple));
		image.add(new ImageView(bannana));
		image.add(new ImageView(cherry));
		image.add(new ImageView(lemon));
		image.add(new ImageView(melon));
		image.add(new ImageView(orange));
		image.add(new ImageView(pineapple));
		image.add(new ImageView(strawberry));
		
		Media correctCardM = new Media("http://codeskulptor-demos.commondatastorage.googleapis.com/descent/gotitem.mp3"),
				wrongCardM = new Media("http://commondatastorage.googleapis.com/codeskulptor-assets/sounddogs/missile.mp3"),
				winM = new Media("http://codeskulptor-demos.commondatastorage.googleapis.com/descent/background%20music.mp3");
		correctCard = new MediaPlayer(correctCardM);
		wrongCard = new MediaPlayer(wrongCardM);
		win = new MediaPlayer(winM);
		
		
		winner.setFont(new Font(25));
		
		//making pane for the buttons (cards)
		
		GridPane game = new GridPane();
		game.setPadding(new Insets(10,10,10,10));
		game.setAlignment(Pos.CENTER);
		game.setHgap(10);
		game.setVgap(10);
		
		
		Collections.shuffle(image);
		for (int i=0 ; i < 16; i++) 
		{
			cards.add(new Card(image.get(i)));
		}
		
		
		
		for(int i =0,r=-1,c=0;i<16;i++,c=(c+1)%4)
		{
			if (c==0)
				r++;
			game.add(cards.get(i), r, c);
		}
		
		
		
		start = new Button("Start");
		start.setMinSize(100, 50);
		Text time = new Text("0 : 00");
		time.setFont(new Font(30));
		Label timer = new Label("Timer: ",time);
		timer.setFont(new Font(30));
		timer.setContentDisplay(ContentDisplay.RIGHT);
		t = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			secs++;
			if (secs == 60)
			{
				mins++;
				secs = 0;
			}
			time.setText(mins+" : "+(secs/10 ==0? "0":"")+secs);
		}));
		t.setCycleCount(Timeline.INDEFINITE);
		
		
		
		
		VBox main = new VBox(20);		// to put the buttons(Cards) and the start button
		main.setAlignment(Pos.CENTER);
		main.getChildren().addAll(game,start);
		
		Scanner scoresFile = new Scanner(new File("Scores.txt"));			// getting scores from the file and put them in their variables
		scoresFile.nextLine();
		topS1 = scoresFile.nextInt()*60;
		scoresFile.next();
		topS1 = topS1 + scoresFile.nextInt();
		topS2 = scoresFile.nextInt()*60;
		scoresFile.next();
		topS2 = topS2 + scoresFile.nextInt();
		topS3 = scoresFile.nextInt()*60;
		scoresFile.next();
		topS3 = topS3 + scoresFile.nextInt();
		Text topScores = new Text("Top 3 Scores");
		
		top1.setText(topS1/60+" : "+(topS1%60/10==0? "0":"" )+topS1%60);
		top2.setText(topS2/60+" : "+(topS2%60/10==0? "0":"" )+topS2%60);
		top3.setText(topS3/60+" : "+(topS3%60/10==0? "0":"" )+topS3%60);
		
		scoresFile.close();
		topScores.setFont(new Font(30));
		top1.setFont(new Font(20));
		top1.setStyle("-fx-background-color: #ffffff;");
		top2.setFont(new Font(20));
		top3.setFont(new Font(20));		
		
		VBox scores = new VBox(30);
		scores.setStyle("-fx-background-color: #93E1D8;");
		scores.setPadding(new Insets(10,10,10,10));
		scores.getChildren().addAll(timer,winner,topScores,top1,top2,top3);
		
		HBox hBox = new HBox(40);
		hBox.setStyle("-fx-background-color: #FFA69E;");
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(main,scores);
		
		start.setOnAction(e -> {
			if (start.getText() == "Start") {
				time.setText("0 : 00");
				for (int i = 0; i < 16; i++)
				{
					cards.get(i).getImageView().setOpacity(1);
					cards.get(i).clickable(true);
					cards.get(i).close(2);
				}
				t.play();
				start.setText("Reset");
				start.setDisable(true);
			} else {
				reset();
				for (int i = 0; i < 16; i++)
				{
					cards.get(i).clickable(false);
					cards.get(i).restStyle();
				}
				start.setText("Start");
			}
		});

		
		Scene s = new Scene(hBox, 900, 600);
		ps.setTitle("Hosam Almutawah ID:201837620 Project");
		ps.setScene(s);
		ps.show();
	}

	public static void main(String[] args) {
		launch(args);

	}

	public void reset() { 					//method to reset the game to play again
		
		Collections.shuffle(image);
		for(int i =0;i<16;i++)
		{
			cards.get(i).setImageView(image.get(i));
			cards.get(i).getImageView().setOpacity(0);
		}
		win.stop();
		secs=0;
		mins=0;
		score = 0;
		winner.setText("");
	}
	
	public void printScores() throws FileNotFoundException {				// method to save new scores in the file
		PrintWriter outFile = new PrintWriter(new File("Scores.txt"));
		outFile.println("Top 3 Scores");
		outFile.println(topS1/60+" : "+(topS1%60/10==0? "0":"" )+topS1%60);
		outFile.println(topS2/60+" : "+(topS2%60/10==0? "0":"" )+topS2%60);
		outFile.print(topS3/60+" : "+(topS3%60/10==0? "0":"" )+topS3%60);
		outFile.close();
	}
	
	private class Card extends BorderPane{
		
		private Button btn;
		private ImageView iv;
		
		public Card(ImageView iv) {
			
			iv.setFitHeight(100);
			iv.setFitWidth(100);
			this.iv = iv;
			btn = new Button();
			btn.setGraphic(iv);
			btn.setStyle("-fx-border-color: #000000;-fx-border-width: 4;-fx-background-color: #ffffff;");
			btn.setDisable(true);
			
			btn.setOnAction(e->{				// Handling when clicked a button (card)
				if(!isOpen())  			
				{
					if(selected == null)
					{
						selected = this;
						open(()->{});
					}
					else
					{
						open(()->{
							if(!isSame(selected))
							{
								wrongCard.seek(Duration.ZERO);
								wrongCard.play();
								close(0.15);
								selected.close(0.15);
							}else {
								correctCard.seek(Duration.ZERO);
								correctCard.play();
								correct();
								selected.correct();
								score++;
								if(score == 8)
								{
									t.stop();
									win.seek(Duration.ZERO);
									win.play();
									start.setDisable(false);
									winner.setText("Winner!!");
									int winnerSecs = mins*60 + secs;
									if (winnerSecs <topS1)
									{
										topS3 = topS2;
										topS2 = topS1;
										topS1 = winnerSecs;
										top1.setText(topS1/60+" : "+(topS1%60/10==0? "0":"" )+topS1%60);
										top2.setText(topS2/60+" : "+(topS2%60/10==0? "0":"" )+topS2%60);
										top3.setText(topS3/60+" : "+(topS3%60/10==0? "0":"" )+topS3%60);
										winner.setText("Winner!!\nNew High\nScore Top1!");
										try {
											printScores();
										} catch (FileNotFoundException e1) {
											e1.printStackTrace();
										}
									}else if(winnerSecs < topS2)
									{
										topS3 = topS2;
										topS2 = winnerSecs;
										top2.setText(topS2/60+" : "+(topS2%60/10==0? "0":"" )+topS2%60);
										top3.setText(topS3/60+" : "+(topS3%60/10==0? "0":"" )+topS3%60);
										winner.setText("Winner!!\nNew High\nScore Top2!");
										try {
											printScores();
										} catch (FileNotFoundException e1) {
											e1.printStackTrace();
										}
									}else if(winnerSecs < topS3)
									{
										topS3 = winnerSecs;
										top3.setText(topS3/60+" : "+(topS3%60/10==0? "0":"" )+topS3%60);
										winner.setText("Winner!!\nNew High\nScore Top3!");
										try {
											printScores();
										} catch (FileNotFoundException e1) {
											e1.printStackTrace();
										}
									}
								}
							}
							selected = null;
						});
						
						
					}
				}
			});
			
			setCenter(btn);
			close(0.15);
		
		}
		
		public void restStyle() {			// to rest style
			btn.setStyle("-fx-border-color: #000000;-fx-border-width: 4;-fx-background-color: #ffffff;");
		}

		public boolean isOpen() {			// check if the card is opened or not
			return iv.getOpacity() == 1;
		}
		
		public boolean isSame(Card card2) {			// check of both cards has the same image
			return this.getImageView().getImage() == card2.getImageView().getImage();
		}
		
		public void open(Runnable action) {			// Transition for opening the card
			FadeTransition ft = new FadeTransition(Duration.seconds(0.15),iv);
			ft.setToValue(1);
			ft.setOnFinished(e-> action.run());
			ft.play();
		}
		public void close(double t) {				// Transition for closing the card
			FadeTransition ft = new FadeTransition(Duration.seconds(t),iv);
			ft.setToValue(0);
			ft.play();
		}
		
		public void setImageView(ImageView iv) {
			this.iv = iv;
			btn.setGraphic(iv);
		}
		
		public ImageView getImageView() {
			return iv;
		}
		
		
		
		public void clickable(Boolean b) {		// change if the button is clickable or not
			btn.setDisable(!b);
		}
		
		public void correct() {					// change the style to correct style
			btn.setStyle("-fx-border-color: #00ff00;-fx-border-width: 4;-fx-background-color: #ffffff;");
		}
		
	}
	
	
	
}
