package in.mobme.tvticker.helpers;

import in.mobme.tvticker.data_model.Media;
import in.mobme.tvticker.database.TvTickerDBAdapter;
import android.content.Context;
import android.util.Log;

public class DataMocker {
	Media media;
	Context ctx = null;
	TvTickerDBAdapter mockAdapter = null;
	final String TAG = "TVTICKER_DATAMOCKER";
	final String[] CHANNELS = { "STAR MOVIES", "HBO", "SURYA", "MOVIES NOW",
			"BBC", "CNBC", "DISCOVERY", "STAR WORLD", "TEN SPORTS" };
	final String[] CATEGORIES = { "MOVIES", "NEWS", "TV SERIES", "DOCUMENTARY",
			"ENTERTAINMENT", "ANIME" };
	final String[] SERIES = { "NO", "YES" };

	public DataMocker(Context ctx) {
		this.ctx = ctx;
		mockAdapter = new TvTickerDBAdapter(ctx);
	}

	public boolean startMocking() {
		mockAdapter.open();
		populateChannels();
		populateCategories();
		// populateSeries();
		populateMainTable();
		mockAdapter.close();
		return true;
	}

	private void populateChannels() {
		Log.i(TAG, "Populating channels..");
		for (String channel : CHANNELS) {
			mockAdapter.insertNewChannel(channel);
		}
		Log.i(TAG, "Done");
	}

	private void populateCategories() {
		Log.i(TAG, "Populating categories..");
		for (String category : CATEGORIES) {
			mockAdapter.insertNewCategory(category);
		}
		Log.i(TAG, "Done");
	}

	// public void populateSeries() {
	// Log.i(TAG, "Populating series..");
	// for (String series : SERIES) {
	// mockAdapter.insertNewChannel(series);
	// }
	// Log.i(TAG, "Done");
	// }

	private void populateMainTable() {
		Log.i(TAG, "Populating Main table..");
		long _id = mockAdapter
				.createNewMediaInfo(mockMediaFor(
						"The Twilight Saga: Breaking Dawn - Part 1",
						"The Quileute's close in on expecting parents Edward and Bella, whose unborn child poses a threat to the Wolf Pack and the towns people of Forks. ",
						"http://static.igossip.com/photos_2/september_2011/breaking_dawn_poster_smaller.jpg",
						"http://www.imdb.com/title/tt1324999/reviews", 4.8f, 1,
						"11:30", "12:45", "117 min", 1, 4));
		mockAdapter.setIsFavorite(_id, false);
		_id = mockAdapter
				.createNewMediaInfo(mockMediaFor(
						"Con Air",
						"A newly released ex-con and former US Ranger finds himself trapped in a prisoner transport plane when the passengers seize control.",
						"http://static.igossip.com/photos_2/january_2011/conair_smaller.jpg",
						"http://www.imdb.com/title/tt0118880/reviews", 6.7f, 1,
						"13:00", "3:45", "115 min", 1, 2));
		mockAdapter.setIsFavorite(_id, false);
		_id = mockAdapter
				.createNewMediaInfo(mockMediaFor(
						"Spider-Man 3 ",
						"A strange black entity from another world bonds with Peter Parker and causes inner turmoil as he contends with new villains, temptations, and revenge. ",
						"http://www.mobilejacker.com/Mobile/Wallpapers/spider-man-3-128x128.jpg",
						"http://www.imdb.com/title/tt0413300/reviews", 6.9f, 1,
						"9:00", "11:00", "113 min", 1, 2));
		mockAdapter.setIsFavorite(_id, false);
		_id = mockAdapter
				.createNewMediaInfo(mockMediaFor(
						"F.R.I.E.N.D.S",
						"The lives, loves, and laughs of six young friends living in Manhattan.",
						"http://24.media.tumblr.com/avatar_e982ccceb2ee_128.png",
						"http://www.imdb.com/title/tt0108778/reviews/", 8.9f,
						1, "17:30", "18:30", "46 min", 1, 8));
		mockAdapter.setIsFavorite(_id, false);
		_id = mockAdapter
				.createNewMediaInfo(mockMediaFor(
						"WWF-Smackdown",
						"Weekly sports show, with frequent commentary, interviews, side plots and hype. ",
						"http://imalbum.aufeminin.com/album/D20080719/447899_QGCFR1SFRC7PMUMMPHEOOCUG88ECHA_smackdown-logo_H161616_S.jpg",
						"http://www.imdb.com/title/tt0227972/reviews", 6.3f, 5,
						"17:30", "19:10", "118 min", 0, 9));
		mockAdapter.setIsFavorite(_id, false);

		_id = mockAdapter
				.createNewMediaInfo(mockMediaFor(
						"The Cove",
						"Using state-of-the-art equipment, a group of activists, led by renowned dolphin trainer Ric O'Barry, infiltrate a cove near Taijii, Japan to expose both a shocking instance of animal abuse and a serious threat to human health. ",
						"http://www.documentarytube.com/wp-content/uploads/2011/09/the-cove-documentary-128x128.jpg",
						"http://www.imdb.com/title/tt1313104/reviews", 8.5f, 4,
						"15:00", "16:30", "92 min", 0, 7));
		mockAdapter.setIsFavorite(_id, false);

		_id = mockAdapter
				.createNewMediaInfo(mockMediaFor(
						"Catwoman",
						"A shy woman, endowed with the speed, reflexes, and senses of a cat, walks a thin line between criminal and hero, even as a detective doggedly pursues her, fascinated by both of her personas. ",
						"http://msn.mess.be/data/media/169/catwoman32.jpg",
						"http://www.imdb.com/title/tt0327554/reviews", 3.2f, 0,
						"21:30", "23:50", "104 min", 0, 1));
		mockAdapter.setIsFavorite(_id, false);

		_id = mockAdapter
				.createNewMediaInfo(mockMediaFor(
						"Spider-Man (2002)",
						"When bitten by a genetically modified spider, a nerdy, shy, and awkward high school student gains spider-like abilities that he eventually must use to fight evil as a superhero after tragedy befalls his family. ",
						"http://4mobifun.com/media/1038-spiderman-3.jpg",
						"http://www.imdb.com/title/tt0145487/reviews", 7.4f, 0,
						"17:10", "19:50", "121 min", 0, 3));
		mockAdapter.setIsFavorite(_id, false);

		_id = mockAdapter
				.createNewMediaInfo(mockMediaFor(
						"Spirited Away",
						"In the middle of her family's move to the suburbs, a sullen 10-year-old girl wanders into a world ruled by gods, witches, and monsters; where humans are changed into animals; and a bathhouse for these creatures. ",
						"http://s03.jigsawplanet.com/i/080c0f000305a00500899b1b76d4fcb36e/128/Spirited-away-1.jpg",
						"http://www.imdb.com/title/tt0245429/reviews", 8.6f, 5,
						"16:30", "18:00", "125 min", 0, 1));
		mockAdapter.setIsFavorite(_id, false);

		Log.i(TAG, "Done");
	}

	private Media mockMediaFor(String title, String desc, String thumb,
			String imdb_url, float rating, int category, String showTime,
			String show_end, String duration, int series_id, int channel_id) {
		media = new Media();
		media.setCategoryType(category);
		media.setChannel(channel_id);
		media.setImdbLink(imdb_url);
		media.setImdbRating(rating);
		media.setMediaDescription(desc);
		media.setMediaThumb(thumb);
		media.setMediaTitle(title);
		media.setSeriesID(series_id);
		media.setShowDuration(duration);
		media.setShowEndTime(show_end);
		media.setShowTime(showTime);
		return media;
	}

}
