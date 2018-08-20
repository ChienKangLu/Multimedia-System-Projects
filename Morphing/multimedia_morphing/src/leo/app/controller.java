package leo.app;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import leo.app.modal.GifSequenceWriter;
import leo.app.modal.Line;
import leo.app.modal.Line_pair;
import leo.app.modal.Vector;

public class controller implements Initializable{
	private static final String Selfdrawing = "Self drawing";
	FileChooser fileChooser;
	@FXML
    private ImageView source;
	@FXML
    private ImageView des;
	@FXML
    private Button source_btn;
	@FXML
    private Button des_btn;
	@FXML
    private TextField a_input;
	@FXML
    private TextField b_input;
	@FXML
    private TextField p_input;
	@FXML
	private ComboBox<String>line_input;
	@FXML
    private TextField frame_input;
	@FXML
    private Button last;
	@FXML
    private Button next;
	@FXML
	ImageView final_gif;
	Mat source_mat;
	Mat des_mat;
	File sourcefromfile;
	File destinationfromfile;
	ArrayList<Line_pair> totalLinePair=new ArrayList<>();
	double a=1;
	double b=2;
	double p=0;
	final double width=300;
	final double height=321;
    public static int frame_number=5;
    private int point_count=0;//點擊的記數，每兩點算一條線
    Stack<Point> pointStack=new Stack<>();//放入point位置
    private int line_count=0;//線的記數，每兩條線算一個pair
    Stack<Line> lineStack=new Stack<>();//放入Line
    ArrayList<Mat>everyfrme=new ArrayList<>();
    int showframenow=frame_number-1;
	@FXML
	private ImageView show_pic;
    @FXML
    protected void clearall(ActionEvent event){
    	////////////////////////////
        point_count=0;//點擊的記數，每兩點算一條線
        pointStack.clear();//放入point位置
        line_count=0;//線的記數，每兩條線算一個pair
        lineStack.clear();//放入Line
        everyfrme.clear();
        showframenow=frame_number-1;
        source.setImage(null);
        des.setImage(null);
        final_gif.setImage(null);
        show_pic.setImage(null);
    	source_mat=null;
    	des_mat=null;
		deletealltemp();
    	
    } 
    @FXML
    protected void bar_choose_source(ActionEvent event){
    	////////////////////////////
    	source_btn_choose(event);
    } 
    @FXML
    protected void bar_choose_des(ActionEvent event){
    	////////////////////////////
    	des_btn_choose(event);
    } 
    @FXML
    protected void source_btn_choose(ActionEvent event){
    	////////////////////////////
		fileChooser=new FileChooser();
		sourcefromfile = this.fileChooser.showOpenDialog(MainApp.getPrimaryStage());
//		sourcefromfile =new File("D:\\NTUST\\Multimedia Systems\\bradpit2.jpg");
		if(sourcefromfile!=null){
			Image image = new Image(sourcefromfile.toURI().toString());
			
			source.setImage(image);
			source_mat = Highgui.imread(sourcefromfile.getAbsolutePath());
	        System.out.println(sourcefromfile.getAbsolutePath().toString());
	        source.setImage(mat2Image(source_mat));
	        totalLinePair.clear();//清空
		}
    }
    @FXML
    protected void des_btn_choose(ActionEvent event){
    	////////////////////////////
		fileChooser=new FileChooser();
		destinationfromfile = this.fileChooser.showOpenDialog(MainApp.getPrimaryStage());
//		destinationfromfile =new File("D:\\NTUST\\Multimedia Systems\\tiger2.jpg");
		if(destinationfromfile!=null){
			Image image = new Image(destinationfromfile.toURI().toString());
			des.setImage(image);
			des_mat = Highgui.imread(destinationfromfile.getAbsolutePath());
	        System.out.println(destinationfromfile.getAbsolutePath().toString());
	        des.setImage(mat2Image(des_mat));
	        totalLinePair.clear();//清空
		}
    }
	private Image mat2Image(Mat frame)
	{		// create a temporary buffer
		MatOfByte buffer = new MatOfByte();
		// encode the frame in the buffer, according to the PNG format
		Highgui.imencode(".png", frame, buffer);
		// build and return an Image created from the image encoded in the
		// buffer
		return new Image(new ByteArrayInputStream(buffer.toArray()));
	}
    @FXML
    protected void drawline(MouseEvent event){
    	Mat now_mat;
    	ImageView now_img;
    	if(event.getSource().toString().contains("source")){
    		now_mat=source_mat; 
    		now_img=source;
    	}else{
    		now_mat=des_mat;
    		now_img=des;
    	}
//	    	System.out.println("click");
//	    	System.out.println(event.getX()+","+event.getY());
	    	pointStack.push(new Point(event.getX(),event.getY()*-1));//變成真實世界的第四象限
	    	point_count++;
	    	if(point_count==2){
		        point_count=0;
	    		Point point2=pointStack.pop();
	    		Point point1=pointStack.pop();
		        Core.line(now_mat, new Point(point1.x,point1.y*-1), new Point(point2.x,point2.y*-1), new Scalar(0,255,0), 2);
		        Core.circle(now_mat, new Point(point1.x,point1.y*-1), 3, new Scalar(0,0,255),-1);//start point (red)
		        Core.circle(now_mat, new Point(point2.x,point2.y*-1), 3, new Scalar(255,0,0),-1);//end point(blue)
		        now_img.setImage(mat2Image(now_mat));
		        lineStack.push(new Line(point1,point2));
		        lineStack.peek().PQ2MiddleDegreeLength();
		        line_count++;
		        if(line_count==2){
		        	line_count=0;
		        	Line line2=lineStack.pop();
		        	Line line1=lineStack.pop();
		        	totalLinePair.add(new Line_pair(line1, line2));
//		        	calculateWrapLine
		        	
		        	totalLinePair.get(totalLinePair.size()-1).calculateWrapLine();//計算中間漸變的線
//		        	for(int i=0;i<frame_number;i++){//顯示中間漸變的線
//		        		Line n=totalLinePair.get(totalLinePair.size()-1).getWrapline().get(i); 
//		        		Core.line(des_mat,Vector.draw_original_pos(n.getP_First()),Vector.draw_original_pos(n.getQ_Second()), new Scalar(0,255,0), 2);
//				        des.setImage(mat2Image(des_mat));
//		        		Core.line(source_mat,Vector.draw_original_pos(n.getP_First()),Vector.draw_original_pos(n.getQ_Second()), new Scalar(0,255,0), 2);
//				        source.setImage(mat2Image(source_mat));
//		        	}
		        	
		        	System.out.println(totalLinePair.get(totalLinePair.size()-1));
		        	
		        }
		        
	    	}
    	
    }
    @FXML
    protected void last_frame(ActionEvent event){
    	if(showframenow>0){//4<5
    		showframenow--;
    		show_pic.setImage(mat2Image(everyfrme.get(showframenow%frame_number)));
    	}    	
    }
    @FXML
    protected void next_frame(ActionEvent event){
    	if(showframenow<frame_number-1){//4<5
    		showframenow++;
    		show_pic.setImage(mat2Image(everyfrme.get(showframenow%frame_number)));
    	}
    }
    
    @FXML
    protected void morphing(ActionEvent event){
		deletealltemp();
    	System.out.println("good");
    	a=Integer.parseInt(a_input.getText());
    	b=Integer.parseInt(b_input.getText());
    	p=Integer.parseInt(p_input.getText());
    	frame_number=Integer.parseInt(frame_input.getText());
    	System.out.println(a+"~"+b+"~"+p);
    
    	if(!line_input.getValue().equals(Selfdrawing)){
    		defaultLine(line_input.getValue());//使用舊的線
    	}else{
        	System.out.println("self");
        	//存起來線
        	String save=totalLinePair.toString();

        	save=save.replaceAll("\\{", "[");
			save=save.replaceAll("\\}", "]");
        	//save="{"+save+"}";
        	System.out.println("");
        	System.out.println(save);
        	 FileWriter fw;
			try {
//				System.out.println(sourcefromfile.getName().re);
				URL url =controller.class.getResource("resources/");
				File parentDirectory = new File(new URI(url.toString()));
				File txt=new File(parentDirectory, "tt.txt");
				fw = new FileWriter(txt);
    	        fw.write(save);
    	        System.out.println(save);
    	        fw.flush();
    	        fw.close();
			} catch (IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


        	
    	}
    	System.out.println(line_input.getValue());
    	
    	
//    	 System.exit(0);
    	
//    	image_processing_test();
    	//main 
    	Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
		    	for(int i=0;i<frame_number;i++){
		    		morphing(i);
		    	}
		    	creategif();
		    	
			}
		});
    	t.start();
    	//main
    	
    	//show_pic.setImage(mat2Image(togreyimage(source_mat)));
    }
    private void creategif(){
		try {
	    	
	        File a[]=new File[frame_number+2];//加上頭尾
    		URL url =controller.class.getResource("image/");
    		File parentDirectory = new File(new URI(url.toString()));
	        for(int i=0;i<frame_number+2;i++){//0 1 2 3 4 
	        	if(i==0){//0
		        	a[i]=sourcefromfile;
	        	}else if(i==frame_number+2-1){//最後一張//4
	        		a[i]=destinationfromfile;
	        	}else{//frame //1 2 3 
	        		File image=new File(parentDirectory,(i+1)+".jpg" );
		        	a[i]=image;
	        	}
	        }
//	        for(int i=0;i<frame_number+2;i++){
//	        	System.out.println(i+"~~"+a[i].getName());
//	        }

	        BufferedImage firstImage;
				firstImage = ImageIO.read(a[0]);
	
	
	        // create a new BufferedOutputStream with the last argument
			
			File z=new File(parentDirectory,"z.gif" );
	        ImageOutputStream output = 
	          new FileImageOutputStream(z);
	        
	        // create a gif sequence with the type of the first image, 1 second
	        // between frames, which loops continuously
	        GifSequenceWriter writer = 
	          new GifSequenceWriter(output, firstImage.getType(), 200, true);//200,5
	        // write out the first image to our sequence...
	        writer.writeToSequence(firstImage);
	        for(int i=1; i<a.length; i++) {
	        	System.out.println(i+a[i].getName());
	          BufferedImage nextImage = ImageIO.read(a[i]);
	          writer.writeToSequence(nextImage);
	        }
	        for(int i=a.length-2; i>=0; i--) {
	            BufferedImage nextImage = ImageIO.read(a[i]);
	            writer.writeToSequence(nextImage);
	          }
	        System.out.println("@@");
	        writer.close();
	        output.close();
				Image image = new Image(z.toURI().toString());
				final_gif.setImage(image);
		       
			
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    private void defaultLine(String filename){
    	//File txt=new File(controller.class.getResource("resources/line.txt").toString());
    	//System.out.println(controller.class.getResource("resources/line.txt"));
    	FileReader fr;
		try {
			File file = new File(controller.class.getResource(filename).toURI());
//			System.out.println(controller.class.getResource("resources/line.txt").toURI());
//			System.out.println(controller.class.getResource("resources/line.txt").toString());
			fr = new FileReader(file);//D:/workspace/multimedia_morphing/bin/leo/app/resources/line.txt
			JSONParser jsonParser=new JSONParser();
		    JSONArray jsonArray = (JSONArray) jsonParser.parse(fr);
	    	Mat now_mat;
	    	ImageView now_img;
		    for(int i=0;i<jsonArray.size();i++){
//		    	System.out.println(jsonArray.get(i));
			    JSONArray linepair = (JSONArray) jsonArray.get(i);
			    Line Line[]=new Line[2];
			    for(int j=0;j<2;j++){
			    	JSONArray line = (JSONArray) linepair.get(j);
//			    	System.out.println(line);
			    	Point p[]=new Point[2]; 
			    	for(int k=0;k<2;k++){
				    	JSONArray point = (JSONArray) line.get(k);
//				    	System.out.println(point);
				    	p[k]=new Point(Double.parseDouble(point.get(0).toString()),Double.parseDouble(point.get(1).toString()));
				    }
			    	if(j==0){
			    		now_mat=source_mat; 
			    		now_img=source;
			    	}else{
			    		now_mat=des_mat;
			    		now_img=des;
			    	}
			    	
			    	Core.line(now_mat, new Point(p[0].x,p[0].y*-1), new Point(p[1].x,p[1].y*-1), new Scalar(0,255,0), 2);
			        Core.circle(now_mat, new Point(p[0].x,p[0].y*-1), 3, new Scalar(0,0,255),-1);//start point (red)
			        Core.circle(now_mat, new Point(p[1].x,p[1].y*-1), 3, new Scalar(255,0,0),-1);//end point(blue)
			        now_img.setImage(mat2Image(now_mat));
			        
			    	Line[j]=new Line(p[0], p[1]);
			    }
			    Line[0].PQ2MiddleDegreeLength();
			    Line[1].PQ2MiddleDegreeLength();
			    totalLinePair.add(new Line_pair(Line[0], Line[1]));
			    totalLinePair.get(totalLinePair.size()-1).calculateWrapLine();//計算中間漸變的線
			    System.out.println(totalLinePair.get(totalLinePair.size()-1));
		    }
		    //totalLinePair.add(new Line_pair(line1, line2));
		    
			
		} catch (IOException | URISyntaxException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    private void morphing(int frameNow){
    	double ratio=(double)(frameNow+1)/(frame_number+1);
    	Mat source_mat_original= Highgui.imread(sourcefromfile.getAbsolutePath());
    	Mat destination_mat_original= Highgui.imread(destinationfromfile.getAbsolutePath());
    	Mat now=destination_mat_original;
    	//新產生中間的漸變圖片
    	Mat newpic=new Mat();
    	newpic.create(321, 300, CvType.CV_64FC3);
    	
    	for(int i=0;i< now.rows();i++){
        	for(int j=0;j< now.cols();j++){
        		Point x=new Point(j,i*-1);
//	        		Point x=new Point(96,137*-1);//測試用
//	        		Point x=new Point(97,167*-1);//測試用
//	        		Core.circle(des_mat, new Point(96,137), 3, new Scalar(0,255,255),-1);//測試用
//        		Core.circle(des_mat, x, 3, new Scalar(0,255,255),-1);//測試用
//    			des.setImage(mat2Image(des_mat));
    			
    			Point DSUM=new Point(0,0);
    			double weightsum=0;
    			
    			Point DSUM2=new Point(0,0);
    			double weightsum2=0;

//    			Core.circle(source_mat, Vector.draw_original_pos(x), 3, new Scalar(0,255,255),-1);
//    			source.setImage(mat2Image(source_mat));
        		for(int k=0;k<totalLinePair.size();k++){//每一條特徵線
        			
//        			Line dst_line=totalLinePair.get(k).getWrapline().get(frameNow);
        			/////source to destination
        			double u=totalLinePair.get(k).calculate_u(x,0,frameNow);//比例
        			double v=totalLinePair.get(k).calculate_v(x,0,frameNow);//長度(帶有方向性)，但是會被抵銷成正確放向的~超酷的
        			//System.out.println(x+"_"+"u="+u);
        			//System.out.println(x+"_"+"v="+v);
        			Point newX=totalLinePair.get(k).getNewX(u, v,0);
        			//System.out.println("newX="+newX);
//	        			Core.circle(source_mat, Vector.draw_original_pos(newX), 3, new Scalar(0,255,255),-1);
//	        			source.setImage(mat2Image(source_mat));
        			Point displacement=Vector.getV(x, newX);//x為destination原來的點
//	        			System.out.println(displacement);
//	        			mypointline(source,source_mat, x, newX);
        			
        			double weight=totalLinePair.get(k).getWeight(newX,a, b, p,0,frameNow);
//        				System.out.println("weight="+weight);
        			DSUM=Vector.add(DSUM,Vector.mutiple(weight, displacement));
        			weightsum+=weight;
//        			break;
        			//中斷測試
//        			Scanner input=new Scanner(System.in);
//        			String a=input.nextLine();
//        			System.exit(0);
        			
        			//////////////////////
        			//version2  destination to source
        			/////////////////////
        			
//        			Core.circle(source_mat, new Point(97,167), 3, new Scalar(0,255,255),-1);//測試用
//        			source.setImage(mat2Image(source_mat));
        			double u2=totalLinePair.get(k).calculate_u(x,1,frameNow);//比例
        			double v2=totalLinePair.get(k).calculate_v(x,1,frameNow);//長度(帶有方向性)，但是會被抵銷成正確放向的~超酷的

        			Point newX2=totalLinePair.get(k).getNewX(u2, v2,1);
        			
//        			System.out.println("newX="+newX2);
        			
//        			Core.circle(des_mat, Vector.draw_original_pos(newX2), 3, new Scalar(0,255,255),-1);
//        			des.setImage(mat2Image(des_mat));
        			
        			Point displacement2=Vector.getV(x, newX2);//x為destination原來的點
        			

//        			mypointline(des,des_mat, x, newX2);
        			
        			
        			double weight2=totalLinePair.get(k).getWeight(newX2,a, b, p,1,frameNow);
        			DSUM2=Vector.add(DSUM2,Vector.mutiple(weight2, displacement2));
        			weightsum2+=weight2;
        		}
        		Point findX=Vector.add(x, Vector.divide(weightsum, DSUM));
//    			System.out.println("findX="+findX);
    			Vector.boundary(findX,width,height);//計算有無超出邊界，並且調整回圖座標
//        		Core.circle(source_mat, findX, 3, new Scalar(12,12,12),-1);
//    			source.setImage(mat2Image(source_mat));
//    			System.out.println("findX="+findX);
    			Point findX2=Vector.add(x, Vector.divide(weightsum2, DSUM2));
    			Vector.boundary(findX2,width,height);//計算有無超出邊界，並且調整回圖座標

//    			System.out.println("findX2="+findX2);
//        		Core.circle(des_mat, findX2, 3, new Scalar(255,3,255),-1);
//    			des.setImage(mat2Image(des_mat)); 
    			
    			
    			
    			//x(x)　 和x'(findX) 做混和//其中最後在算出點X’時如果有小數點的話，利用bilinear interpolation來取得色彩值
    			//double color_dest[]=destination_mat_original.get((int)x.y*-1,(int)x.x );
    			double color_dest[]=Vector.bilinear_interpolation(destination_mat_original, findX2,width,height);
    			double color_source[]=Vector.bilinear_interpolation(source_mat_original, findX,width,height);
    			double newcolor[]=new double[3];
    			for(int q=0;q<3;q++){
    				newcolor[q]=color_dest[q]*(ratio)+color_source[q]*(1-ratio);
    			}
//    			newcolor=color_dest;
//    			newcolor=color_source;

				newpic.put(i, j,newcolor);

            	System.out.println("("+i+","+j+")");
//        		break;
        	}
//        	break;
    	}
    	
    	show_pic.setImage(mat2Image(newpic));
    	everyfrme.add(newpic);
		//中斷測試
//		Scanner input=new Scanner(System.in);
//		String a=input.nextLine();
		URL url =controller.class.getResource("image/");
		File parentDirectory;
		try {
			parentDirectory = new File(new URI(url.toString()));
			File image=new File(parentDirectory,(frameNow+2)+".jpg" );
	    	Highgui.imwrite(image.toString(), newpic);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    	System.out.println("over");
    }
    void mypointline(ImageView im,Mat m,Point start,Point end){
        Core.line(m, Vector.draw_original_pos(start), Vector.draw_original_pos(end), new Scalar(255,255,0), 2);
        Core.circle(m, Vector.draw_original_pos(start), 1, new Scalar(0,0,255),-1);//start point (red)
        Core.circle(m, Vector.draw_original_pos(end), 1, new Scalar(255,0,0),-1);//end point(blue)
		im.setImage(mat2Image(m));
        //System.out.println("+1");
        //System.out.println(start+"~~"+end);
    }
    private Mat togreyimage(Mat mat){
        System.out.println(mat.size());
        System.out.println(mat.rows());
        System.out.println(mat.cols());
        for(int i=0;i<mat.rows();i++){
        	for(int j=0;j<mat.cols();j++){
        		double color[]=mat.get(i, j);
        		if(i==299&&j==300)
        			System.out.println(mat.get(i, j));
        		double grey = color[2]*0.299 + color[1]*0.587 + color[0]*0.114;
        		color[0]=grey;//blue
        		color[1]=grey;//green
        		color[2]=grey;//red
        		mat.put(i,j,color);
            	System.out.println("i="+i+","+"j="+j);
        	}
        }
        return mat;
    }
    private void image_processing_test(){
    	Mat mat=new Mat();
    	mat.create(321, 300, CvType.CV_64FC1);//.CV_64FC3
        for(int i=0;i<mat.rows();i++){
        	for(int j=0;j<mat.cols();j++){
            	mat.put(i,j, 60);
        	}
        }
    	show_pic.setImage(mat2Image(mat));
    	
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		System.out.println("Initializable");
		deletealltemp();
		a_input.setText("1");
		b_input.setText("2");
		p_input.setText("0");
		ArrayList<String> ff=new ArrayList<>();
		ff.add(Selfdrawing);
		String[] filename=all_line();
		for(int i=0;i<filename.length;i++){
			ff.add(filename[i]);
		}
		ObservableList<String> options = 
			    FXCollections.observableArrayList(ff);

		line_input.setItems(options);
//		File file = new File(controller.class.getResource("resources/line9.txt").toURI());
		 
		
	}
	   @FXML
	    protected void dvalue(ActionEvent event){
		   a_input.setText("1");
			b_input.setText("2");
			p_input.setText("0");
	    }
	public String[] all_line(){
		String[] directories=null;
		try {
			File file = new File(controller.class.getResource("resources/").toURI());
			directories = file.list(new FilenameFilter() {
				  @Override
				  public boolean accept(File current, String name) {
				    return new File(current, name).isFile();
				  }
				});
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i=0;i<directories.length;i++) {
			directories[i]="resources/"+directories[i];
		}

		System.out.println(Arrays.toString(directories));
		return directories;
	}
	public void deletealltemp(){
		String[] directories=null;
		File getallfile[]=null;
		try {
			
			File file = new File(controller.class.getResource("image/").toURI());
			
			getallfile=file.listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File file) {
					// TODO Auto-generated method stub
					return file.isFile();
				}
			});
			for (File f : getallfile) {
				f.delete();
			}

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
}

