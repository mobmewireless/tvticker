package in.mobme.tvticker.data_model.rules;

public interface Channel{
	int CHANNEL_UNDEFINED = 0;
	int CHANNEL_1 = 1;
	int CHANNEL_2 = 2;

	void setChannels(int[] onChannels);

	int[] getChannels();
}
