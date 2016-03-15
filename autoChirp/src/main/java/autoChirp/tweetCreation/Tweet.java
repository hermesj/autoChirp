package autoChirp.tweetCreation;

import autoChirp.DBConnector;

/**
 * 
 * represents a single tweet consisting of content, tweetDate, tweetID, groupID,
 * groupName and the status-attributes scheduled and tweeted
 * 
 * @author Alena Geduldig
 */
public class Tweet implements Comparable<Tweet> {
	public String tweetDate;
	public String content;
	public int tweetID;
	public int groupID;
	public boolean scheduled;
	public boolean tweeted;
	public String groupName;
	public String imageUrl;
	public float longitude;
	public float latitude;

	/**
	 * Constructor for tweets read from the database. In contrast to new tweets
	 * tweets read from DB already have a tweetID, groupID, userID and
	 * status-attributes
	 * 
	 * @param tweetDate
	 *            scheduling date of the tweet
	 * @param content
	 *            the tweets content
	 * @param tweetID
	 *            db-key
	 * @param groupID
	 *            db-group-assignment key
	 * @param scheduled
	 *            tweet is already scheduled and will be tweeted
	 * @param tweeted
	 *            tweet has already been tweeted
	 * @param userID
	 *            db-user-assignment key
	 */
	public Tweet(String tweetDate, String content, int tweetID, int groupID, boolean scheduled, boolean tweeted,
			int userID, String imageUrl, float longitude, float latitude) {
		this.tweetDate = tweetDate;
		this.content = content;
		this.tweetID = tweetID;
		this.scheduled = scheduled;
		this.tweeted = tweeted;
		this.groupID = groupID;
		this.groupName = DBConnector.getGroupTitle(groupID, userID);
		this.imageUrl = imageUrl;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	/**
	 * Constructor for new tweets, which were not stored in the database yet.
	 * 
	 * @param tweetDate
	 *            scheduling date of the tweet
	 * @param content
	 *            the tweets content
	 */
	public Tweet(String tweetDate, String content, String imageUrl, float longitude, float latitude) {
		this.tweetDate = tweetDate;
		this.content = content;
		this.imageUrl = imageUrl;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public Tweet(String tweetDate, String content){
		this.tweetDate = tweetDate;
		this.content = content;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Tweet t) {
		return this.tweetDate.compareTo(t.tweetDate);
	}
}
