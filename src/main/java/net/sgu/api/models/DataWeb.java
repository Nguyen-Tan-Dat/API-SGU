    package net.sgu.api.models;

    import com.gargoylesoftware.htmlunit.BrowserVersion;
    import com.gargoylesoftware.htmlunit.WebClient;
    import com.gargoylesoftware.htmlunit.html.HtmlPage;
    import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
    import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
    import org.jsoup.Jsoup;
    import org.jsoup.nodes.Document;
    import org.jsoup.select.Elements;

    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.List;

    import static java.lang.Thread.sleep;

    public class DataWeb {
        private WebClient webClient;
        private HtmlPage page;

        public DataWeb() {
            this.setup();
            this.connect();
        }

        private void setup() {
            this.webClient = new WebClient(BrowserVersion.CHROME);
            this.webClient.getOptions().setUseInsecureSSL(true);
            this.webClient.getOptions().setJavaScriptEnabled(true);
            this.webClient.getOptions().setCssEnabled(true);
            this.webClient.getOptions().setThrowExceptionOnScriptError(false);
            this.webClient.getOptions().setRedirectEnabled(true);
            this.webClient.getCookieManager().setCookiesEnabled(true);
            this.webClient.setJavaScriptTimeout(60000);
        }

        private void connect() {
            try {
                page = this.webClient.getPage("http://thongtindaotao.sgu.edu.vn/");
                HtmlTextInput inputUser = page.getElementByName("ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtTaiKhoa");
                HtmlPasswordInput inputPass = page.getElementByName("ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtMatKhau");
                inputUser.setValueAttribute("3118410076");
                inputPass.setValueAttribute("D@t16/05/2000");
                waitLoadPage();
                page = page.getElementByName("ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$btnDangNhap").click();
                waitLoadPage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void waitLoadPage() {
            while (!page.getReadyState().equals("complete")) {
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void requestTDK(String info) {
            for (int i = 0; i < 3; i++) {
                try {
                    page = this.webClient.getPage("http://thongtindaotao.sgu.edu.vn/Default.aspx?page=dkmonhoc");
                    HtmlTextInput inputMaMH1 = page.getHtmlElementById("txtMaMH1");
                    inputMaMH1.setValueAttribute(info);
                    page = page.getHtmlElementById("btnLocTheoMaMH1").click();
                    webClient.waitForBackgroundJavaScript(60000);
                    return;
                } catch (Exception e) {
                    this.connect();
                }
            }
        }

        public String getTDK() {
            return page.getHtmlElementById("divTDK").asXml();
        }

        public List<String[]> fromTDKtoData(String divTDK) {
            Document tdk = Jsoup.parse(divTDK);
            Elements tds = tdk.getElementsByTag("td");
            int size = tds.size();
            List<String[]> result = new ArrayList<>(size / 17 + 1);
            for (int i = 0; i < size; i += 17) {
                String[] data = new String[17];
                for (int j = 0; j < data.length; j++)
                    data[j] = tds.get(i + j).text();
                result.add(data);
            }
            return result;
        }

        public List<HocPhan> fromTDKtoList(String divTDK) {
            Document tdk = Jsoup.parse(divTDK);
            Elements tds = tdk.getElementsByTag("td");
            int size = tds.size();
            List<HocPhan> result = new ArrayList<>(size / 17 + 1);
            for (int i = 0; i < size; i += 17) {
                String[] data = new String[17];
                for (int j = 0; j < data.length; j++)
                    data[j] = tds.get(i + j).text();
                int conLai = 0;
                try {
                    conLai = Integer.parseInt(data[9]);
                } catch (NumberFormatException ignored) {
                }
                if (conLai > 0)
                    result.add(HocPhan.toHocPhan(data));
            }
            return result;
        }

        public List<HocPhan> getData(String info) {
            requestTDK(info);
            return fromTDKtoList(getTDK());
        }

        public void close() {
            this.webClient.close();
        }
    }
