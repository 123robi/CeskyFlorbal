import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetData {
	    public static void main(String[] args) {
	    	List<Match> matches = new ArrayList<>();
	    	Document doc = null;
	    	//219700
	    	//245701
	    	for (int i = 219700; i <= 245800; i++) {
		    	List<Penalty> penalties = new ArrayList<>();
		    	Match match = null;
	    		try {
			    	doc = Jsoup.connect("https://online.ceskyflorbal.cz/20182019/online-" + i + ".htm").get();

			    	Elements details = doc.select(".leftRecord70-values");
			    	boolean canSave = false;
			    	for(Element element : details) {
			    		if(element.text().contains("Tipsport Superliga")) {
			    			canSave = true;
			    		}
			    		if(element.text().contains("kolo") && canSave) {
			    			match = new Match(element.text(), doc.select(".home-team-name").text(), doc.select(".away-team-name").text());
			    		}

			    	}
			    	if (canSave) {
			    		Elements spans = doc.select(".goal .onlinerecord-text");
				    	boolean save = false;
				    	Penalty penalty = null;
				    	for(Element element : spans) {
				    		if (save) {
				    			penalties.get(penalties.indexOf(penalty)).setScoreBefore(element.text().substring(element.text().indexOf("(") + 1, element.text().indexOf(")")));
				    			save = false;
				    		}
					    	penalty = null;
				    		if(element.text().contains("Trestné støílení družstva")) {
				    			penalty = new Penalty(
				    					element.parent().parent().parent().select(".onlinetime_goal span").text(),
				    					element.text().substring(element.text().indexOf("nepromìnil") + 11).replace(".", ""),
				    					element.text().substring(element.text().indexOf("družstva") + 9, element.text().indexOf("nepromìnil") - 1),
				    					false);
				    		} else if (element.text().contains("z trestného støílení")) {
				    			penalty = new Penalty(
				    					element.parent().parent().parent().select(".onlinetime_goal span").text(),
				    					element.text().substring(element.text().indexOf("vstøelil") + 9, element.text().indexOf(" z ")),
				    					element.text().substring(element.text().indexOf("družstva") + 9, element.text().indexOf("vstøelil") - 1),
				    					true);
				    		}
				    		if (penalty != null)  {
				    			save = true;
					    		penalties.add(penalty);
				    		}
				    	}
				    	
				    	Elements penaltyShoots = doc.select(".penaltyshoots");
				    	for (Element element : penaltyShoots) {
					    	penalty = null;
				    		if(element.text().contains("ale gól nevstøelil")) {
				    			penalty = new Penalty(
				    					"OVERTIME",
				    					element.text().substring(element.text().indexOf("hráè") + 4, element.text().indexOf(" , ale gól")),
				    					element.text().substring(element.text().indexOf("družstva") + 9, element.text().indexOf("hráè") - 1),
				    					false);
				    		} else if (element.text().contains("a vstøelil gól") || element.text().contains("a vstøelil rozhodující gól")) {
				    			penalty = new Penalty(
				    					"OVERTIME",
				    					element.text().substring(element.text().indexOf("hráè") + 4, element.text().indexOf("a vstøelil")),
				    					element.text().substring(element.text().indexOf("družstva") + 9, element.text().indexOf("hráè") - 1),
				    					true);
				    		}
				    		if (penalty != null)  {
					    		penalties.add(penalty);
				    		}
				    	}
			    	}
				} catch (Exception e) {
					System.out.println(i);
				}
		    	if (penalties.size() != 0) {
		    		match.setPenalties(penalties);
		    		matches.add(match);
		    	} 
	    	}
	    	
	    	  try (PrintWriter writer = new PrintWriter(new File("test1.csv"))) {

	    	      StringBuilder sb = new StringBuilder();
	    	      sb.append("kolo");
	    	      sb.append(',');
	    	      sb.append("home");
	    	      sb.append(',');
	    	      sb.append("away");
	    	      sb.append(',');
	    	      sb.append("player");
	    	      sb.append(',');
	    	      sb.append("time");
	    	      sb.append(',');
	    	      sb.append("isGoal");
	    	      sb.append(',');
	    	      sb.append("Team");
	    	      sb.append(',');
	    	      sb.append("Score before");
	    	      sb.append('\n');

	    	      writer.write(sb.toString());

    	    	  StringBuilder sb1 = new StringBuilder();
	    	      for (Match match : matches) {
		    	     
		    	      for(Penalty penalty : match.getPenalties()) {
		    	    	  sb1.append(match.getKolo());
			    	      sb1.append(',');
			    	      sb1.append(match.getHome());
			    	      sb1.append(',');
			    	      sb1.append(match.getAway());
			    	      sb1.append(',');
			    	      sb1.append(penalty.getPlayer());
			    	      sb1.append(',');
			    	      sb1.append(penalty.getTime());
			    	      sb1.append(',');
			    	      sb1.append(penalty.isGoal());
			    	      sb1.append(',');
			    	      sb1.append(penalty.getTeam());
			    	      sb1.append(',');
			    	      sb1.append(penalty.getScoreBefore());
			    	      sb1.append('\n');
		    	      }
	  	    		}

	    	      writer.write(sb1.toString());

	    	    } catch (FileNotFoundException e) {
	    	      System.out.println(e.getMessage());
	    	    }
	    
	    	
	    }
}
