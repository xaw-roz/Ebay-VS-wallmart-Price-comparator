import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by rocks on 4/9/2017.
 */
public class GetFromEbayServlet extends javax.servlet.http.HttpServlet {
        class TitleAndPrice{
            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            String title;
            String price;
        }
        ArrayList<TitleAndPrice> ebayTitleAndPrice=new ArrayList<>();
        ArrayList<Elements> ebayDetails=new ArrayList<>();
        ArrayList<Element> ebayHrefs=new ArrayList<>();
        int index=0;
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException,ArrayIndexOutOfBoundsException, IOException,NullPointerException {
        String page=request.getParameter("page");

        if(page.equalsIgnoreCase("urlsubmit"))
        {
            index=0;
            ebayTitleAndPrice.clear();
            ebayDetails.clear();
            ebayHrefs.clear();
            String soldUrl="m.html?_nkw=&_armrs=1&_ipg=&_from=&LH_Complete=1&LH_Sold=1&rt=nc&LH_BIN=1";
            String url=request.getParameter("url");
            String user[] =url.split("usr/");
            String usr="";
            if(user[1].contains("?"))
            {

                usr=user[1].split("\\?")[0];
            }
            else
            {
                usr=user[1];
            }
            System.out.println("https://www.ebay.com/sch/"+usr+"/"+soldUrl);

            Document doc = Jsoup.connect("https://www.ebay.com/sch/"+usr+"/"+soldUrl).get();



            Elements hrefs=doc.getElementsByClass("vip");

            for (Element href : hrefs) {
                //   System.out.println(href);
                //System.out.println(href.attr("class"));
                ebayHrefs.add(href);
                Document itemPage = Jsoup.connect(href.attr("href")).get();
                ebayDetails.add(itemPage.getElementsByClass("itemAttr"));
                try {
                    TitleAndPrice titleAndPrice = new TitleAndPrice();
                    titleAndPrice.setTitle(itemPage.getElementById("itemTitle").text().replaceAll("Details about  ",""));
//                    System.out.println(itemPage.getElementById("itemTitle").text().replaceAll("Details about  ",""));

                    if(itemPage.getElementById("mm-saleDscPrc")!=null)
                    {
                        titleAndPrice.setPrice(itemPage.getElementById("mm-saleDscPrc").text());
                    }
                    else {
                        titleAndPrice.setPrice(itemPage.getElementById("prcIsum").text());
                    }

                    //  System.out.println(itemPage.getElementById("prcIsum").text());
                    ebayTitleAndPrice.add(titleAndPrice);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if(doc.getElementsByClass("gspr next").size()!=0) {
                Document itemPage=null;
                Elements hrefsInLoop=null;
                while (doc.getElementsByClass("gspr next").size() != 0) {
                    //                System.out.println(doc.getElementsByClass("gspr next").first().attr("href") + "next page");
                    doc = Jsoup.connect(doc.getElementsByClass("gspr next").first().attr("href")).get();

                    hrefsInLoop= doc.getElementsByClass("vip");


                    for (Element href : hrefsInLoop) {
                        //   System.out.println(href);
                        //System.out.println(href.attr("class"));
                        ebayHrefs.add(href);
                        //                  System.out.println(href);
                        itemPage = Jsoup.connect(href.attr("href")).get();
                        ebayDetails.add(itemPage.getElementsByClass("itemAttr"));
                        try {
                            TitleAndPrice titleAndPrice = new TitleAndPrice();
                            titleAndPrice.setTitle(itemPage.getElementById("itemTitle").text().replaceAll("Details about  ",""));
                            //                    System.out.println(itemPage.getElementById("itemTitle").text().replaceAll("Details about  ",""));
                            titleAndPrice.setPrice(itemPage.getElementById("prcIsum").text());
                            //                  System.out.println(itemPage.getElementById("prcIsum").text());
                            ebayTitleAndPrice.add(titleAndPrice);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
            System.out.println("completed");
            RequestDispatcher rd  = request.getRequestDispatcher("/app?page=next");
            rd.forward(request,response);
            return;

        }
         if(request.getParameter("page").equalsIgnoreCase("next"))
        {
            TitleAndPrice ebayItem=ebayTitleAndPrice.get(index);
            String ebayTitle=ebayItem.getTitle();
            String ebayprice=ebayItem.getPrice();
            String ebayInfo=ebayDetails.get(index).toString();
            String encodedTitle= URLEncoder.encode(ebayTitle, "UTF-8");
            String searchPage="https://www.walmart.com/search/?page=1&query="+encodedTitle+"&sort=best_match#searchProductResult";
            //System.out.println(searchPage);
            Document doc=Jsoup.connect(searchPage).get();
            Elements walmartHrefs=doc.getElementsByClass("product-title-link line-clamp line-clamp-3");
            int loopindex=0;
            String outputHref="";
            System.out.println(ebayHrefs);
            System.out.println(walmartHrefs.size()+"ebay href");
            System.out.println(index+"index");
            if(index<ebayHrefs.size()) {
            //System.out.println("inside first if");
            Document walmartItem = null;
            for (int i = 0; i < walmartHrefs.size(); i=i+2) {
                System.out.println(walmartHrefs.attr("href")+"walmart hrefs");
                //  System.out.println("inside for");
                if (loopindex < 10) {
                    //System.out.println("inside if");
                    String walhref = walmartHrefs.get(loopindex).attr("href");
                    System.out.println("wallmart href"+walhref);
                    walmartItem = Jsoup.connect("https://walmart.com" + walhref).get();
                    //     System.out.println(walmartItem);
                    String name = walmartItem.getElementsByClass("prod-ProductTitle no-margin heading-a").first().toString();
                    String price = walmartItem.getElementsByClass("display-inline-block arrange-fit Price Price--stylized u-textColor").toString();
                    String desc = walmartItem.getElementsByClass("table table-striped-odd specification").first().toString();
                    //   System.out.println(price + "  price");
                        System.out.println(name + "  name");
                       System.out.println(desc + "desc");

                    outputHref=outputHref+"<a href='"+"https://walmart.com" + walhref+"'>"+name+"</a><br>";

                    outputHref=outputHref+"<div style=\"font-size: xx-large\">Price : "+price+"</div><br><br>";
                    outputHref=outputHref+"<h2>description</h2><div style=\"font-size: xx-small\">"+desc+"</div><br><br><hr>";

                    loopindex=loopindex+2;

                } else {
                    break;
                }
            }

            request.setAttribute("ebayTitle","<h1><a href='"+ebayHrefs.get(index).attr("href").toString()+"'>"+ebayTitle+"</a></h1>");
            request.setAttribute("ebayPrice", ebayprice);
            request.setAttribute("ebayInfo", ebayInfo);
            request.setAttribute("walmartInfo",outputHref);
            //  System.out.println(outputHref+"outpute");
            index++;
        }
            RequestDispatcher requestDispatcher=request.getRequestDispatcher("views/compare.jsp");
            requestDispatcher.forward(request,response);
            return;



        }
         if(request.getParameter("page").equalsIgnoreCase("prev")) {
            TitleAndPrice ebayItem = ebayTitleAndPrice.get(index);
            String ebayTitle = ebayItem.getTitle();
            String ebayprice = ebayItem.getPrice();
            String ebayInfo = ebayDetails.get(index).toString();
            String encodedTitle = URLEncoder.encode(ebayTitle, "UTF-8");
            String searchPage = "https://www.walmart.com/search/?page=1&query=" + encodedTitle + "&sort=best_match#searchProductResult";
            //System.out.println(searchPage);
            Document doc = Jsoup.connect(searchPage).get();
            Elements walmartHrefs = doc.getElementsByClass("product-title-link line-clamp line-clamp-3");
            int loopindex = 0;
            String outputHref = "";
            System.out.println(ebayHrefs);
            System.out.println(walmartHrefs.size()+"ebay href");
            System.out.println(index+"index");

            if (index >0) {
                //System.out.println("inside first if");
                Document walmartItem = null;
                for (int i = 0; i < walmartHrefs.size(); i++) {
                    //  System.out.println("inside for");
                    if (loopindex < 5) {
                        //System.out.println("inside if");
                        String walhref = walmartHrefs.get(loopindex).attr("href");
                        //   System.out.println("wallmart href"+walhref);
                        walmartItem = Jsoup.connect("https://walmart.com" + walhref).get();
                        //     System.out.println(walmartItem);
                        String name = walmartItem.getElementsByClass("prod-ProductTitle no-margin heading-a").first().toString();
                        String price = walmartItem.getElementsByClass("display-inline-block arrange-fit Price Price--stylized u-textColor").first().toString();
                        String desc = walmartItem.getElementsByClass("table table-striped-odd specification").first().toString();
                        System.out.println(price + "  price");
                        System.out.println(name + "  name");
                       System.out.println(desc + "desc");

                        outputHref = outputHref + "<a href='" + "https://walmart.com" + walhref + "'>" + name + "</a><br>";

                        outputHref = outputHref + "<div style=\"font-size: xx-large\">Price : " + price + "</div><br><br>";
                        outputHref = outputHref + "<h2>description</h2><div style=\"font-size: xx-small\">" + desc + "</div><br><br><hr>";

                        loopindex++;

                    } else {
                        break;
                    }
                }

                request.setAttribute("ebayTitle", "<h1><a href='" + ebayHrefs.get(index).attr("href").toString() + "'>" + ebayTitle + "</a></h1>");
                request.setAttribute("ebayPrice", ebayprice);
                request.setAttribute("ebayInfo", ebayInfo);
                request.setAttribute("walmartInfo", outputHref);
                //  System.out.println(outputHref+"outpute");
                index--;
            }
            RequestDispatcher requestDispatcher=request.getRequestDispatcher("views/compare.jsp");
            requestDispatcher.forward(request,response);
             return;

        }
        if(request.getParameter("page").equalsIgnoreCase("ebayscrap"))
        {
            String output="";
            System.out.println(ebayHrefs.size()+"ebay href size");
            for (int i = 0; i < ebayHrefs.size(); i++) {
                output=output+"<h1><a href='" + ebayHrefs.get(i).attr("href").toString() + "'>" + ebayTitleAndPrice.get(i).getTitle() + "</a></h1><br><br>";
                output=output+"<h2>Price : "+ebayTitleAndPrice.get(i).getPrice()+"</h2><br>";
                output=output+ebayDetails.get(i);
                output=output+"<br><br><hr>";
            }
            request.setAttribute("output",output);
        }
        RequestDispatcher requestDispatcher=request.getRequestDispatcher("views/ebayscrap.jsp");
        requestDispatcher.forward(request,response);
        return;

    }


    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request,response);
    }
}
