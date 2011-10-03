package in.mobme.tvticker.data_model.rules;

public interface Category  {

	int TYPE_UNDEFINED = 0;
	int TYPE_MOVIES = 1;
	int TYPE_SPORTS = 2;

	void setCategoryType(int type);

	int getCategoryType();

}
