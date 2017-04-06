/**
Author:  Meryem Sena Bark

* Date:    December 2015

*/

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class EditRooms {
	
	private int[][] distance= new int[10][10];
	private int numberofroom=0;
	private int dc=0;
	private int[] sortedA;
	
	ArrayList <String> Inputs= new ArrayList<String>();
	
	GraphTS graph = new GraphTS();
	Nodes node= new Nodes();


	class Node{
		int nRoom;
		String maxWeight;
		int[] weight;
		int[] value;
		Node left;
		Node right;
		
		public Node(int numberofRoom, String mweight,int[] w, int[] v){
		
			nRoom=numberofRoom;
			maxWeight=mweight;
			weight=w;
			value=v;
			left=null;
			right=null;
		}
	}

	
	class Nodes{
	
		protected Node root;		
	
		public Nodes(){
			root=null;
		}
				
		public void add(int id, String mweight,int[] w,int[] v){
			root=insert(root,id,mweight,w,v);
	
		}
	
		public Node insert(Node T,int id, String mweight,int[] w,int[] v){
	
	
			if(T==null){
				return new Node(id,mweight,w,v);
			}else if(T.nRoom>id)
				T.left=insert(T.left,id, mweight,w,v);		
			else T.right=insert(T.right,id, mweight,w,v);
				return T;
			}
	
		public Node SearchNode(int key){
		
			return SearchNode(root,key);
		}
	
		private Node SearchNode(Node roott,int key){
	
			if(roott==null) return roott;
				else if(roott.nRoom==key) return roott;
					else if(roott.nRoom>key){
						return SearchNode(roott.left,key);
					}else{
						return SearchNode(roott.right,key);
					}	
		}	

	
		public  boolean SearchBNode(Node roott, int key){
		
			if(roott==null) return false;
				else if(roott.nRoom==key) return true;
					else if(roott.nRoom>key){
						return SearchBNode(roott.left,key);
					}else{
						return SearchBNode(roott.right,key);
					}	
		}	
	}
	
	
	public void ReadFile(String fileName) throws IOException{
		
		FileInputStream fstream = null;
		String[] tmp=null;

	
		try{       
                fstream = new FileInputStream(fileName);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                String strLine=" ";
                 int count=0;	
             
                
                 while ((strLine = br.readLine()) != null){
                	 Inputs.add(count,strLine);
                	 count++;                  
                 } 
                 br.close();
                 
                 for(int i=0; i<10; i++){
                
                	if((Inputs.get(i).toString().indexOf("\t"))>0){
                		tmp=Inputs.get(i).toString().split("\t");       		                 			            
                 for(int t=0; t<tmp.length; t++){
                	 distance[dc][t]=Integer.parseInt(tmp[t]);}
                	 dc++;               	 
                 }else break;
             numberofroom=i+1;    
             graph.addVertex(numberofroom);
             }
         System.out.println();
		}catch(Exception e){
			System.out.println(e.getMessage());}	
		}
	
	
	public void Findway(){
		for(int k=0; k<distance.length; k++){
			for(int j=0; j<distance[k].length; j++){
				if(distance[k][j]!=0){
					graph.addEdge(k, j);
				}
			}
		}
		graph.topo();

	}
         

	public void callKnapsack(){
	
		String[] temp= new String[numberofroom-1];   //for taking weights from the file.
		String[] temp2= new String[2];            //for taking value and weight of item
		int[] weight=new int [10];
		int[] value = new int[10];
		int count=0;
		int cc=1;
		int numberofitem=0;
		
		sortedA = new int[graph.getSortedArray().length];
    
		sortedA=graph.getSortedArray();

    	
		for(int t=numberofroom; t<Inputs.size();t++){
    	
			if((Inputs.get(t).toString().indexOf("\t"))<0){
		
				if(count!=0) {
		    		//	System.out.println(count+"c");
		    		node.add(count, temp[count-1], weight, value);  //adding node with items in the graph
					weight= new int[10];
					value= new int[10];
					cc=1;
				}
    		temp[count]=Inputs.get(t).toString();
    		count++; 	
    		
			}else{
	    		temp2=Inputs.get(t).toString().split("\t");
	    		weight[cc]=Integer.parseInt(temp2[0]);
	    		value[cc]=Integer.parseInt(temp2[1]);
	    		cc++;
			}   	
		}
		
	    node.add(count, temp[count-1], weight, value);
		weight= new int[10];
		value= new int[10];
	  	Knapsack k= new Knapsack();
	   
	  	for(int s=0; s<sortedA.length; s++){
           int a[]=null;
           int b[]=null;

           if(node.SearchBNode(node.root, sortedA[s])==true){
        	   if(sortedA[s]==node.SearchNode(sortedA[s]).nRoom){	
	    		  a= node.SearchNode(sortedA[s]).weight ;
	    	      b=node.SearchNode(sortedA[s]).value;
    		
	    	      for(int ff=0; ff<a.length; ff++){
	    	    	  if(a[ff]!=0)
    	    		   numberofitem++;
	    	      }
    	
	    	      k.Knapsack(numberofitem, node.SearchNode(sortedA[s]).maxWeight, a, b);
	    	      numberofitem=0;
        	   }
           }
	  	}
  	
	  	System.out.println("The total gain is: " +k.getLastTotalValue()+" TL");

	}

}