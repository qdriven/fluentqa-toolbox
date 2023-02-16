package io.fluent.qe.utils;

public class LinkUtility {


   public static String makeALink(String text, String uri) {
      return "<a href='" + uri + "'>" + text + "</a>";
   }

}