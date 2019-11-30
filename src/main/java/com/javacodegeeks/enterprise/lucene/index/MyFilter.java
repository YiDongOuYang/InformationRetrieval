package com.javacodegeeks.enterprise.lucene.index;

import java.io.File;

import java.util.List;

 //import com.sun.xml.internal.bind.v2.runtime.output.NamespaceContextImpl;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class MyFilter {     //自建类，使用dom4j将文档转化为相应的域
	
	private static File urlfile;

	private static Document document;

	public int hasTitle;

	public int hasAbstract;

	public int hasText;

	public int hasPublishTime;
	
	public MyFilter(File file) throws DocumentException {
		urlfile = file;
		document = parse(urlfile);
		hasTitle = 0;
		hasText = 0;
		hasAbstract = 0;
		hasPublishTime = 0;
	}
	
	private Document parse(File urlf) throws DocumentException{
		SAXReader reader = new SAXReader();
		reader.setEncoding("GB2312");
		Document doc = reader.read(MyFilter.urlfile);
		return doc;
	}

	public String getTitle(){
		Element root = document.getRootElement();
		String rev = "";
		try{
			Element titleElement = root.element("teiHeader").element("fileDesc").element("titleStmt").element("title");
			rev = titleElement.getText();
		}
		catch (Exception e){
			System.out.println(e.getStackTrace());
			System.out.println("-@Title");
		}
		
		if(!rev.equals("")) hasTitle=1;

		return rev;
	}


	public String getAbstract(){
		Element root = document.getRootElement();
		String rev = "";
		try {
			Element abstractElement = root.element("teiHeader").element("profileDesc").element("abstract").element("div");
			List<Element> ab_list = abstractElement.elements("p");
			for(Element e:ab_list){
				rev+=e.getText();
			}
		}
		catch (Exception e){
			System.out.println(e.getStackTrace());
			System.out.println("-@Abstract");
		}

		if(!rev.equals("")) hasAbstract=1;
		return rev;
		
	}
	public String getText(){
		Element root = document.getRootElement();
		String rev = "";
		try {
			Element bodyElement = root.element("text").element("body");
			List<Element> div_list=bodyElement.elements("div");
			for(Element e_d:div_list){
				rev+=e_d.getText();
				List<Element> p_list = e_d.elements("p");
				for(Element e_p:p_list){
					rev+=e_p.getText();
				}
			}
		}
		catch (Exception e){
			System.out.println(e.getStackTrace());
			System.out.println("-@Text");
		}
		
		if(!rev.equals("")) hasText=1;
		return rev;
	}

	public String getPublishTime(){
		Element root = document.getRootElement();
		String rev = "";
		try{
			Element dateElement = root.element("teiHeader").element("fileDesc").element("publicationStmt").element("date");
			rev = dateElement.getText();
		}
		catch (Exception e){
			System.out.println(e.getStackTrace());
			System.out.println("-@PublishTime");
		}

		if(!rev.equals("")) hasPublishTime=1;

		return rev;
	}

	public String getPDF(){
		String[] split_name = urlfile.getName().split("/.");//urlfile.getName().split("\\.");
		String folder = urlfile.getParent();
		String PDFFile = folder+"/"+split_name[0].replaceAll(".tei.xml", "")+".pdf";//folder+"\\"+split_name[0]+".pdf";
		System.out.println(PDFFile);
		return PDFFile;
	}
/*
	public String getAuthor(){
		Element root = document.getRootElement();
		String rev = "";
		ArrayList<String> nameList = new ArrayList<>();
		try{
			 Iterator<Element> authorElements = root.element("teiHeader").element("fileDesc").element("sourceDesc").element("biblStruct")
					.element("analytic").elementIterator("author");
			 while(authorElements.hasNext()){
			 	Element this_element = authorElements.next();
			 	nameList.add(this_element.getText());
			 }
			 if (nameList.size()!=0){
			 	for(String str :nameList) rev+=str;
			 }
			 else rev+="0";
			return rev;
		}
		catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println("-@Author");
		}

		return rev;
	}*/


	public String getMeta(){
		return "";
	}
	public String showTitle(){
	    System.out.println("Start");
        Element root = document.getRootElement();
        Node title_l=  document.selectSingleNode("/TEI/teiHeader/fileDesc/titleStmt/title");
        return new String("Finished "+title_l.getText());
    }



	public static void main(String args[]) throws DocumentException {
		MyFilter filter = new MyFilter(new File("H:\\InformationRetrieval\\Test\\IndexTest\\Files\\" +
                "kbosuedudspacebitstream181164441smelkerhonorsthesis.tei.xml"));
		System.out.println(filter.getPublishTime());
	}

}
