package luckycoins.misc;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.util.HashMap;

import luckycoins.LuckyCoins;

public class Results
{
	public static final HashMap<String, EnumResult> results = new HashMap<String, Results.EnumResult>();
	
	public static void success(String name)
	{
		results.put(name, EnumResult.SUCCESS);
	}
	
	public static void fail(String name)
	{
		results.put(name, EnumResult.FAIL);
	}
	
	public static void wait(String name)
	{
		results.put(name, EnumResult.WAITING);
	}
	
	public static EnumResult get(String name)
	{
		return results.get(name);
	}
	
	public static void writeToByteBuf(String name, ByteBuf buf)
	{
		EnumResult result = get(name);
		buf.writeByte(result.id);
	}
	
	public static EnumResult readFromByteBuf(ByteBuf buf)
	{
		byte id = buf.readByte();
		switch (id)
		{
		case 0:
			return EnumResult.WAITING;
		case 1:
			return EnumResult.SUCCESS;
		case 2:
			return EnumResult.FAIL;
		}
		String error = "Failed to read EnumResult from byte! Returning WAITING.";
		LuckyCoins.logger.warn(error);
		IOException t = new IOException(error);
		LuckyCoins.logger.catching(t);
		return EnumResult.WAITING;
	}
	
	public static enum EnumResult
	{
		WAITING((byte)0),
		SUCCESS((byte)1),
		FAIL((byte)2);
		
		private byte id;
		
		private EnumResult(byte id)
		{
			this.id = id;
		}
	}
}