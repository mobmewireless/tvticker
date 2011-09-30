package in.mobme.data_model.rules;

public interface Category  {

	int CATEGORY_UNDEFINED = 0;
	int CATEGORY_MOVIES = 1;
	int CATEGORY_SPORTS = 2;

	void setCategoryType(int type);

	int getCategoryType();

}
