package autoChirp.tweetCreation;

/**
 * 
 * @author Alena
 *represents a single tweet
 */
public class Tweet implements Comparable<Tweet> {
	public String tweetDate;
	public String content;
	public int tweetID;
	public boolean scheduled;
	public boolean tweeted;
	
	public Tweet(String tweetDate, String content, int tweetID, boolean scheduled, boolean tweeted){
		this.tweetDate = tweetDate;
		this.content = content;
		this.tweetID = tweetID;
		this.scheduled = scheduled;
		this.tweeted = tweeted;
	}
	
	public Tweet(String tweetDate, String content){
		this.tweetDate = tweetDate;
		this.content = content;
	}
	
	
	public String getTweetDate() {
		return tweetDate;
	}
	public void setTweetDate(String tweetDate) {
		this.tweetDate = tweetDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}


	@Override
	public int compareTo(Tweet t) {
		return this.tweetDate.compareTo(t.tweetDate);
	}

	public int getTweetID() {
		// TODO Auto-generated method stub
		return tweetID;
	}
}
