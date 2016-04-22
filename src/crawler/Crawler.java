package crawler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

import static utils.Constants.*;

public class Crawler {

	private List<String> urlWaiting = new ArrayList<String>();		//A list of URLs that are waiting to be processed
	private List<String> urlProcessed = new ArrayList<String>();	//A list of URLs that were processed
	private List<String> urlError = new ArrayList<String>();		//A list of URLs that resulted in an error
	
	private int numFindUrl = 0;		//find the number of url

	private final SimpleDateFormat sFormat = new SimpleDateFormat(DATE_FORMAT);

	public Crawler() {}

	
	/**
	 * start crawling
	 */
	public void begin() {
		
		while (!urlWaiting.isEmpty()) {
			processURL(urlWaiting.remove(0));
		}
		
//		log("finish crawling");
//		log("the number of urls that were found:" + numFindUrl);
//		log("the number of urls that were processed:" + urlProcessed.size());
//		log("the number of urls that resulted in an error:" + urlError.size());
	}

	/**
	 * Called internally to process a URL
	 * 
	 * @param strUrl
	 *            The URL to be processed.
	 */
	public void processURL(String strUrl) {
		URL url = null;
		try {
			url = new URL(strUrl);
			//log("Processing: " + url);
			
			// get the URL's contents
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", USER_AGENT);

			if ((connection.getContentType() != null)
					&& !connection.getContentType().toLowerCase()
							.startsWith("text/")) {
//				log("Not processing because content type is: "
//						+ connection.getContentType());
				log(TYPE_CONNECTING, url.toString(), TAG_ERROR);
				return;
			}
			log(TYPE_CONNECTING, url.toString(), TAG_SUCCESS);

			// read the URL
			InputStream is = connection.getInputStream();
			Reader r = new InputStreamReader(is);
            log(TYPE_FETCHING, url.toString(), TAG_SUCCESS);
			
			// parse the URL
			HTMLEditorKit.Parser parse = new HTMLParse().getParser();
			parse.parse(r, new Parser(url), true);
            log(TYPE_PARSING, url.toString(), TAG_SUCCESS);
		} catch (IOException e) {
			urlError.add(url.toString());
			//log("Error: " + url);
			log(TYPE_FETCHING, url.toString(), TAG_ERROR);
            return;
		}
		// mark URL as complete
		urlProcessed.add(url.toString());
		//log("Complete: " + url);
	}

	/**
	 * Add a URL to waiting list.
	 * 
	 * @param url
	 */
	public void addURL(String url) {
		if (urlWaiting.contains(url))
			return;
		if (urlError.contains(url))
			return;
		if (urlProcessed.contains(url))
			return;
		//log("Adding to workload: " + url);
		urlWaiting.add(url);
		numFindUrl++;
	}

	/**
	 * Called internally to log information This basic method just writes the
	 * log out to the stdout.
	 * 
	 * @param entry
	 *            The information to be written to the log.
	 */
//	public void log(String entry) {
//		String date = sFormat.format(new Date());
//		System.out.println(USER_AGENT + " " 
//							+ date + " " 
//							+ entry);
//	}
	
	public void log(String type, String url, String tag){
		String date = sFormat.format(new Date());
		System.out.println(USER_AGENT + " "
				+ date + " "
				+ type + " "
				+ url + " " 
				+ tag);
	}
	
	
	protected class HTMLParse extends HTMLEditorKit {
		public HTMLEditorKit.Parser getParser() {
			return super.getParser();
		}
	}

	/**
	 * A HTML parser callback used by this class to detect links
	 * 
	 */
	protected class Parser extends HTMLEditorKit.ParserCallback {
		protected URL base;

		public Parser(URL base) {
			this.base = base;
		}

		public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
			String href = (String) a.getAttribute(HTML.Attribute.HREF);

			if ((href == null) && (t == HTML.Tag.FRAME))
				href = (String) a.getAttribute(HTML.Attribute.SRC);

			if (href == null)
				return;

			int i = href.indexOf('#');
			if (i != -1)
				href = href.substring(0, i);

			if (href.toLowerCase().startsWith("mailto:")) 
				return;

			handleLink(base, href);
		}

		public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
			handleSimpleTag(t, a, pos); // handle the same way

		}

		protected void handleLink(URL base, String str) {
			try {
				URL url = new URL(base, str);
				addURL(url.toString());
			} catch (MalformedURLException e) {
				//log("Found malformed URL: " + str);

			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Crawler crawler = new Crawler();
		crawler.addURL(SEED_URL);
		crawler.begin();
	}
}
