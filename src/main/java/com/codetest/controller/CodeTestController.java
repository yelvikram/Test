package com.codetest.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CodeTestController {
	    private static String  START;
	    private static String  END;
	    private static boolean flag;
	    private static boolean isConnected=false;
	
	@RequestMapping("/connected")
	public String checkCities(@RequestParam(value="origin") String origin,@RequestParam(value="destination") String destination) throws FileNotFoundException {
	 String responseYes = "Yes";
	 String responseNo = "No";
	 HashMap<String,String> cityMap=new HashMap<String, String>();
	 
	 File file = ResourceUtils.getFile("classpath:Cities.txt");
	 Path_Between_Nodes graph = new Path_Between_Nodes();
	  try {
			List<String> allLines = Files.readAllLines(file.toPath());
			for (String line : allLines) {
				String city[]=line.split(",");
				cityMap.put(city[0], city[1]);
				graph.addTwoWayVertex(city[0], city[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	  
	  
      LinkedList<String> visited = new LinkedList();
      START = origin;
      END = destination;

      visited.add(START);
      new Path_Between_Nodes().breadthFirst(graph, visited);
      if(isConnected) {
      	//System.out.println("PATH EXIST");
      	return responseYes;
      }
    
      return responseNo;
	}
	
	
	
	
	 
	public class Path_Between_Nodes
	{
	    private Map<String, LinkedHashSet<String>> map = new HashMap();
	 
	    public void addEdge(String node1, String node2)
	    {
	        LinkedHashSet<String> adjacent = map.get(node1);
	        if (adjacent == null)
	        {
	            adjacent = new LinkedHashSet();
	            map.put(node1, adjacent);
	        }
	        adjacent.add(node2);
	    }
	 
	    public void addTwoWayVertex(String node1, String node2)
	    {
	        addEdge(node1, node2);
	        addEdge(node2, node1);
	    }
	 
	    public boolean isConnected(String node1, String node2)
	    {
	        Set adjacent = map.get(node1);
	        if (adjacent == null)
	        {
	            return false;
	        }
	        return adjacent.contains(node2);
	    }
	 
	    public LinkedList<String> adjacentNodes(String last)
	    {
	        LinkedHashSet<String> adjacent = map.get(last);
	        if (adjacent == null)
	        {
	            return new LinkedList();
	        }
	        return new LinkedList<String>(adjacent);
	    }
	 
	   
	 
	   
	 
	    private void breadthFirst(Path_Between_Nodes graph,
	            LinkedList<String> visited)
	    {
	        LinkedList<String> nodes = graph.adjacentNodes(visited.getLast());
	 
	        for (String node : nodes)
	        {
	            if (visited.contains(node))
	            {
	                continue;
	            }
	            if (node.equals(END))
	            {
	                visited.add(node);
	                printPath(visited);
	                flag = true;
	                visited.removeLast();
	                break;
	            }
	        }
	 
	        for (String node : nodes)
	        {
	            if (visited.contains(node) || node.equals(END))
	            {
	                continue;
	            }
	            visited.addLast(node);
	            breadthFirst(graph, visited);
	            visited.removeLast();
	        }
	        if (flag == false)
	        {
				/*
				 * System.out.println("No path Exists between " + START + " and " + END);
				 */    flag = true;
	        }
	 
	    }
	 
	    private void printPath(LinkedList<String> visited)
	    {
	        if (flag == false)
				/*
				 * System.out.println("Yes there exists a path between " + START + " and " +
				 * END);
				 */   isConnected=true;
	        for (String node : visited)
	        {
	            //System.out.print(node);
	            isConnected=true;
	        }
	        //System.out.println();
	    }
	}
	

}
