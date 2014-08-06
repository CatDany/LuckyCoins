package luckycoins.asm;

import java.io.IOException;

import luckycoins.Refs;
import cpw.mods.fml.common.asm.transformers.AccessTransformer;

public class AccessTransformers extends AccessTransformer
{
	public AccessTransformers() throws IOException
	{
		super(Refs.MOD_ID + "_at.cfg");
	}
}