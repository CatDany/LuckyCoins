package luckycoins.api;

import java.util.List;

import cpw.mods.fml.common.event.FMLInitializationEvent;

public interface IApi
{
	/**
	 * Register a coin, make sure to put your modid before the coin name to avoid conflicts
	 * Do in {@link FMLInitializationEvent}
	 * @param coin
	 */
	public void registerCoin(CoinBase coin);
	
	/**
	 * Get a list of all the registered coins
	 * You can modify this List object, it's not bound to an original
	 * @return
	 */
	public List<CoinBase> getCoins();
}