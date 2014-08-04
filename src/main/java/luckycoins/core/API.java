package luckycoins.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import luckycoins.api.CoinBase;
import luckycoins.api.IApi;

public class API implements IApi
{
	@Override
	public void registerCoin(CoinBase coin)
	{
		CoinRegistry.registerCoin(coin);
	}
	
	@Override
	public List<CoinBase> getCoins()
	{
		List list = new ArrayList<CoinBase>();
		Iterator<String> icoin = CoinRegistry.map.keySet().iterator();
		while (icoin.hasNext())
		{
			CoinBase coin = CoinRegistry.getCoin(icoin.next());
			list.add(coin);
		}
		return list;
	}
}