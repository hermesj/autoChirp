package autoChirp.webController;

import autoChirp.tweetCreation.Document;
import autoChirp.tweetCreation.Parser.WikipediaParser;
import autoChirp.tweetCreation.SentenceSplitter;
import autoChirp.tweetCreation.Tweet;
import autoChirp.tweetCreation.TweetFactory;
import autoChirp.tweetCreation.TweetGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("twitter")
public class TestsController {

private ConnectionRepository connectionRepository;
private Twitter twitterConnection;

@Inject
public TestsController(ConnectionRepository connectionRepository, Twitter twitterConnection) {
        this.connectionRepository = connectionRepository;
        this.twitterConnection = twitterConnection;
}

@RequestMapping(value = "/tests", method = RequestMethod.GET)
public ModelAndView tests(Model model) {
        if (!model.containsAttribute("twitter"))
                return new ModelAndView("redirect:/account");

        return new ModelAndView("tests");
}

@RequestMapping(value = "/tests", method = RequestMethod.POST, params = "tweet")
public ModelAndView tweetpost(Model model, @RequestParam("tweet") String tweet) {
        if (!model.containsAttribute("twitter"))
                return new ModelAndView("redirect:/account");

        TimelineOperations timelineOperations = twitterConnection.timelineOperations();
        TweetData tweetData = new TweetData(tweet);
        timelineOperations.updateStatus(tweetData);

        return new ModelAndView("redirect:/tests");
}

@RequestMapping(value = "/tests", method = RequestMethod.POST, params = "url")
public ModelAndView urlpost(Model model, @RequestParam("url") String url) {
        if (!model.containsAttribute("twitter"))
                return new ModelAndView("redirect:/account");

        TweetFactory tweeter = new TweetFactory();
        WikipediaParser parser = new WikipediaParser();
        Document doc = parser.parse(url);
        SentenceSplitter splitter = new SentenceSplitter();
        doc.setSentences(splitter.splitIntoSentences(doc.getText(), doc.getLanguage()));
        TweetGroup group = tweeter.getTweets(doc);
        List<Tweet> tweetsList = group.tweets;
        ModelAndView mv = new ModelAndView("parsetest");
        mv.addObject("tweets", tweetsList);
        return mv;
}


// @RequestMapping(value = "/protoview", method = RequestMethod.POST, params = "url")
// public @ResponseBody ModelAndView protoview(@RequestParam("url") String url) {
//         Document doc = parser.parse(url);
//         SentenceSplitter splitter = new SentenceSplitter();
//         doc.setSentences(splitter.splitIntoSentences(doc.getText(), doc.getLanguage()));
//         List<Tweet> tweetsList = tweeter.getTweets(doc);
//         Map<String, List<String> > tweets = new HashMap<String, List<String> >();
//         for (Tweet tweet : tweetsList) {
//                 List<String> tweetsForDate = tweets.get(tweet.getTweetDate());
//                 if(tweetsForDate == null) {
//                         tweetsForDate = new ArrayList<String>();
//                 }
//                 tweetsForDate.add(tweet.getContent());
//                 tweets.put(tweet.getTweetDate(), tweetsForDate);
//         }
//         ModelAndView mv = new ModelAndView("protoview");
//         mv.addObject("tweets", tweets);
//         return mv;
// }
}