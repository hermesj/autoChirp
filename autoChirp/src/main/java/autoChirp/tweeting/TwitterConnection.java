package autoChirp.tweeting;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

import autoChirp.DBConnector;
import autoChirp.tweetCreation.Tweet;

/**
 * This class executes the actual twitter status-update using
 * Spring Social Twitter API
 * 
 * @author Alena Geduldig
 *
 */
@Component
public class TwitterConnection {

	@Value("${spring.social.twitter.appId}")
	private String appIDProp;

	@Value("${spring.social.twitter.appSecret}")
	private String appSecretProp;

	private static String appID;
	private static String appSecret;

	/**
	 * get appToken and appSecret
	 */
	@PostConstruct
	public void initializeConnection() {
		this.appID = appIDProp;
		this.appSecret = appSecretProp;
	}

	/**
	 * updates the users twitter-status to the tweets content. 1. reads the tweet
	 * with the given tweetID from the database 2. checks if the related
	 * tweetGroup is still enabled and tweet wasn't tweeted already 3. reads the
	 * users oAuthToken and oAuthTokenSecret from the database 4. updates the
	 * users twitter status to the tweets tweetContent
	 * 
	 * @param user_id
	 * 		userID
	 * @param tweetID
	 * tweetID
	 */
	public void run(int userID, int tweetID) {
		Tweet toTweet = DBConnector.getTweetByID(tweetID, userID);
		if (toTweet == null) {
			return;
		}
		// check if tweet was not tweeted already
		if (toTweet.tweeted) {
			return;
		}
		// check if tweetGroup is still enabled
		if (!DBConnector.isEnabledGroup(toTweet.groupID, userID)) {
			return;
		}
		// read userConfig
		String[] userConfig = DBConnector.getUserConfig(userID);
		String token = userConfig[1];
		String tokenSecret = userConfig[2];
		// tweeting with org.springframework.social.twitter
		Twitter twitter = new TwitterTemplate(appID, appSecret, token, tokenSecret);
		twitter.timelineOperations().updateStatus(toTweet.content);
		DBConnector.flagAsTweeted(tweetID, userID);
	}

}
