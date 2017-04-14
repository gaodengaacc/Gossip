package com.gordon.main.http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * @author Gordon
 * @since 2017/4/12
 * do()
 */
public class ResultInterceptor implements Interceptor {
    private Sign sign;

    public ResultInterceptor(Sign sign) {
        this.sign = sign;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Headers originHeaders = request.headers();

        Headers newHeaders = new Headers.Builder()
                .add("Accept-Language", "zh-cn,zh;q=0.5")
                .build();

        request = request.newBuilder()
                .headers(originHeaders)
                .headers(newHeaders)
                .build();
        Response response = chain.proceed(request);
        System.out.println("\n");
        System.out.println(chain.request().method() + " " + chain.request().url());
        System.out.println("-----------------= Request =-----------------");
        System.out.println(chain.request().headers());
        System.out.println(bodyToString(chain.request()));
//        System.out.println("-----------------= Response =-----------------");
//        String bodyString = bodyToString(response.body());
//        System.out.println(bodyString);
        System.out.println("\n");
        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), doResult(sign, bodyToString(chain.request()), response.body())))
                .build();
    }

    private String bodyToString(Request request) {
        try {
            Request copy = request.newBuilder().build();
            Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readString(Charset.forName("gb2312"));
        } catch (final IOException e) {
            return "did not work";
        }
    }

    private String bodyToString(ResponseBody body, String charset) {
        try {
            InputStreamReader r = new InputStreamReader(body.byteStream(), charset);
            StringBuilder b = new StringBuilder();
            int line;
            while ((line = r.read()) != -1) {
                b.append((char) line);
            }
            b.toString();
            return b.toString();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    private String bodyToString(String body) {
        try {
            ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(body.getBytes());
            InputStreamReader r = new InputStreamReader(tInputStringStream, "gb2312");
            StringBuilder b = new StringBuilder();
            int line;
            while ((line = r.read()) != -1) {
                b.append((char) line);
            }
            b.toString();
            return b.toString();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    private String doResult(Sign sign, String requestString, ResponseBody body) {
        System.out.println("-----------------= Response =-----------------");
        String responseString = "";
        String result = "";
        switch (sign) {
            case NameOne:
                responseString = bodyToString(body, "gb2312");
                result = doNameResultOne(requestString.replace("ceming=", ""), responseString);
                break;
            case NameTwo:
                responseString = bodyToString(body, "gb2312");
                result = doNameResultTwo("", responseString);
                break;
            case NameThree:
                responseString = bodyToString(body, "gb2312");
                result = doNameResultThree("", responseString);
                break;
            case NameFour:
                responseString = bodyToString(body, "gb2312");
                result = doNameResultFour("", responseString);
                break;
            case NameFive:
                responseString = bodyToString(body, "gb2312");
                result = doNameResultFive("", responseString);
                break;
            case PhoneOne:
                responseString = bodyToString(body, "utf-8");
                result = doPhoneResult("", responseString);
                break;
            default:
                break;
        }
        System.out.println(requestString);
        System.out.println("\n");
        return result;
    }

    private String doPhoneResult(String s, String requestString) {
        Document doc = Jsoup.parse(requestString);
        String phoneValue = "";
        String isGoodOrBad = "";
        try {
            Element ela = doc.getElementsByTag("dfn").first();
            String temp = ela.text().substring(
                    ela.text().indexOf("的数理为：") + 5);
            phoneValue = temp.substring(temp.indexOf("￥")+1, temp.indexOf("(", temp.indexOf("￥")));
            temp = "<font color='#006400'>"
                    + temp.substring(0, temp.indexOf("(")) + "</font>"
                    + temp.substring(temp.indexOf("("));
            temp = temp.substring(temp.indexOf("幸运星："),
                    temp.indexOf("幸运星：") + 11) + "<br />"
                    + temp.substring(temp.indexOf("幸运星：") + 11)
                    + temp.substring(0, temp.indexOf("幸运星："))
                    + "<br/>";
            temp = temp.substring(0, temp.length() - 3)
                    + "<font color='#FF0000'>"
                    + temp.substring(temp.length() - 3) + "</font>";
//            temp = temp.substring(0, temp.indexOf("￥"))
//                    + "<font color='#FF0000'>" +
//                    temp.substring(temp.indexOf("￥"))
//                    + "</font>";
            temp = temp.replaceAll("★",
                    "<font color='#FF0000'>★</font>");
            temp = temp.replaceAll("幸运星：",
                    "<font color='#006400'>幸运星：</font>");
            try {
                String str = temp.substring(temp.indexOf("号码价值评估："), temp.length());
                if (str.contains("凶"))
                    isGoodOrBad = "凶";
                else
                    isGoodOrBad = "吉";
                temp = temp.replace(str, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            s = temp;
        } catch (Exception e) {

        }
        return "{\"phoneValue\":\"" + phoneValue + "\",\"isGoodOrBad\":\"" + isGoodOrBad + "\",\"content\":\"" + s + "\"}";
    }

    private String doNameResultOne(String name, String responseString) {
        StringBuffer buffer1 = new StringBuffer();
        StringBuffer buffer2 = new StringBuffer();
        Document doc = Jsoup.parse(responseString);

//        Elements elements = ela.getElementsByTag("p");
//        try {
//            buffer1.append(elements.first().text().trim()
//                    + ": <br />");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            Element ela = doc.getElementsContainingOwnText("分析结果如下")
                    .first().parent().parent();
            String temp = ela.getElementsByTag("p").get(1).text()
                    .trim();
            if (temp.endsWith("凶")) {
                temp = temp.substring(0, temp.length() - 1)
                        + "<font color='#FF0000'>凶</font>";
            } else if (temp.endsWith("吉")) {
                temp = temp.substring(0, temp.length() - 1)
                        + "<font color='#006400'>吉</font>";
            }

            buffer1.append(temp.replace("数理", "<font color='#006400'>数理（笔画）</font>") + "<br />");
            for (int i = 0; i < name.length(); i++) {
                buffer2.append(ela.getElementsByTag("tr").get(i + 1)
                        .getElementsByTag("td").first().text()
                        .replaceAll(" ", "")
                        + "：");
                temp = ela.getElementsByTag("tr").get(i + 1)
                        .getElementsByTag("td").get(5).text().trim();
                buffer2.append(temp.replaceAll("（吉）",
                        "（<font color='#006400'>吉</font>）").replaceAll(
                        "（凶）", "（<font color='#FF0000'>凶</font>）"));
                if (i != name.length() - 1) {
                    buffer2.append("<br />");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("\n" + "buffer1" + buffer1.toString() + "buffer2" + buffer2.toString());
        return toJson(buffer1.toString() + buffer2.toString());
    }

    private String doNameResultTwo(String hanzi, String responseString) {
        try {
            hanzi = Jsoup.parse(responseString).getElementsContainingOwnText("巧连神数：")
                    .first().getElementsByTag("font").first().text()
                    .trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toJson("<font color='#006400'>" + hanzi.replace("。", "") + "</font>");
    }

    private String doNameResultThree(String text, String responseString) {
        Document doc = Jsoup.parse(responseString);
        try {
            String ret = doc
                    .getElementsByAttributeValue("class", "lefttop")
                    .first().text().trim();
            text = ret.substring(ret.indexOf("推算结果") + 5)
                    .replace("解签参考 ", " 解签参考：")
                    .replace("签文详解 ", " 签文详解 ：").trim();
            if (text.startsWith("吉")) {
                text = "<font color='#FF0000'>吉</font>"
                        + text.substring(1);
            } else if (text.startsWith("凶")) {
                text = "<font color='#FF0000'>凶</font>"
                        + text.substring(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toJson(text);
    }

    private String doNameResultFour(String s, String responseString) {
        Document doc = Jsoup.parse(responseString);
        try {
            String all = doc
                    .getElementsByAttributeValue("class", "lefttop")
                    .first().text();
            s = all.substring(all.indexOf("推算结果") + 5,
                    all.indexOf("我在用#诸葛神推#")) + "<br/>";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toJson(s);
    }

    private String doNameResultFive(String s, String responseString) {
        StringBuffer buffer = new StringBuffer();
        String nameScore = null;
        try {
            Document doc = Jsoup.parse(responseString);
            nameScore = doc.getElementsByAttributeValue("class", "xm_df")
                    .first().text();
//            buffer.append("<font color='#FF0000'>得分："
//                    + nameScore + "</font>&nbsp;&nbsp;");
            String js = doc
                    .getElementsByAttributeValue("class", "xm_js")
                    .first().text();
            js = js.substring(js.indexOf("五 行"), js.indexOf("简体笔画"))
                    + "&nbsp;&nbsp;"
                    + js.substring(js.indexOf("简体笔画"),
                    js.indexOf("繁体笔画"));
            buffer.append(js + "<br />");
//					Elements elag = doc.getElementsByTag("ul").first()
//							.getElementsByTag("li");
//					for (Element e : elag) {
            // String blue = "<font color='#0000FF'>" +
            // e.text().substring(0, 3) + "</font>";
            // String red = "<font color='#FF0000'>" +
            // e.text().substring(3) + "</font>";
            // result5 += blue + red + "&nbsp;&nbsp;";
//						result5 += e.text() + "&nbsp;&nbsp;";
//					}
//					result5 += "<br />";
            String temp = "";
            Elements ela = doc.getElementsByTag("ul").get(1)
                    .getElementsByTag("li");
            for (int i = 0; i < 5; i++) {
                temp = ela.get(i).text();
                if (temp.endsWith("(吉)")) {
                    temp = temp.substring(0, temp.length() - 3)
                            + "<font color='#FF0000'>(吉)</font>";
                } else if (temp.endsWith("(凶)")) {
                    temp = temp.substring(0, temp.length() - 3)
                            + "<font color='#FF0000'>(凶)</font>";
                } else if (temp.endsWith("(半吉)")) {
                    temp = temp.substring(0, temp.length() - 4)
                            + "<font color='#FF0000'>(半吉)</font>";
                }
                temp = "<font color='#006400'>"
                        + temp.substring(0, temp.indexOf("["))
                        + "</font>" + temp.substring(temp.indexOf("["))
                        + "<br />";
                buffer.append(temp);
            }
            temp = doc.getElementsByTag("ul").get(1).text();
            temp = temp.substring(temp.indexOf("天、人、地三才"),
                    temp.indexOf("更多信息："));
            temp = "<font color='#FF00FF'>"
                    + temp.substring(0, temp.indexOf("（")) + "</font>"
                    + temp.substring(temp.indexOf("（"));
            temp = temp
                    .replace("1、总论：",
                            "<br /><font color='#006400'>1、总论：</font>")
                    .replace("2、性格：",
                            "<br /><font color='#006400'>2、性格：</font>")
                    .replace("3、意志：",
                            "<br /><font color='#006400'>3、意志：</font>")
                    .replace("4、事业：",
                            "<br /><font color='#006400'>4、事业：</font>")
                    .replace("5、家庭：",
                            "<br /><font color='#006400'>5、家庭：</font>")
                    .replace("6、婚姻：",
                            "<br /><font color='#006400'>6、婚姻：</font>")
                    .replace("7、子女：",
                            "<br /><font color='#006400'>7、子女：</font>")
                    .replace("8、社交：",
                            "<br /><font color='#006400'>8、社交：</font>")
                    .replace("9、精神：",
                            "<br /><font color='#006400'>9、精神：</font>")
                    .replace("10、财运：",
                            "<br /><font color='#006400'>10、财运：</font>")
                    .replace("11、健康：",
                            "<br /><font color='#006400'>11、健康：</font>")
                    .replace("12、老运：",
                            "<br /><font color='#006400'>12、老运：</font>");
            buffer.append(temp);

            for (int i = 5; i < ela.size(); i++) {
                temp = ela.get(i).text();
                if (temp.startsWith("人格数理")
                        || temp.startsWith("人格或地格中有")
                        || temp.startsWith("地格数有")
                        || temp.startsWith("人格或地格中有")) {
                    temp = "<br /><font color='#006400'>"
                            + temp.substring(0, temp.indexOf("：") + 1)
                            + "</font>"
                            + temp.substring(temp.indexOf("：") + 1);
                } else if (temp.startsWith("人格与外格")) {
                    temp = "<br /><font color='#006400'>"
                            + temp.substring(0, temp.indexOf("，") + 1)
                            + "</font>"
                            + temp.substring(temp.indexOf("，") + 1);
                } else if (temp.startsWith("人格与天格搭配")
                        || temp.startsWith("人格与地格搭配")) {
                    temp = "<br /><font color='#006400'>"
                            + temp.substring(0, temp.indexOf(")") + 1)
                            + "</font>"
                            + temp.substring(temp.indexOf(")") + 1);
                }
                buffer.append(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{\"nameScore\":\"" + nameScore + "\",\"content\":\"" + buffer.toString() + "\"}";
    }

    private String toJson(String result) {
        return "{\"content\":\"" + result + "\"}";
    }

    public enum Sign {
        NameOne(1),
        NameTwo(2),
        NameThree(3),
        NameFour(4),
        NameFive(5),
        PhoneOne(11),
        PhoneTwo(12),
        PhoneThree(13),;

        private Sign(int value) {
        }
    }
}
