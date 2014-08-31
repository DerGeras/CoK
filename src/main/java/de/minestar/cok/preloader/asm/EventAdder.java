package de.minestar.cok.preloader.asm;

import java.util.HashMap;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import de.minestar.cok.preloader.CoreLogHelper;
import de.minestar.cok.preloader.PreloaderReference;
import de.minestar.cok.util.LogHelper;

public class EventAdder implements IClassTransformer{

	@Override
	public byte[] transform(String className, String newClassName, byte[] bytes) {
		if(className.equals(PreloaderReference.ISobf.get("itemStackClassName"))){
			return transformItemStack(bytes, PreloaderReference.ISobf);
		}
		if(className.equals(PreloaderReference.ISdeobf.get("itemStackClassName"))){
			return transformItemStack(bytes, PreloaderReference.ISdeobf);
		}
		return bytes;
	}
	
	public byte[] transformItemStack(byte[] bytes, HashMap<String, String> ref){
		CoreLogHelper.info("Patching Itemstack!");
		
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		
		for(MethodNode method : classNode.methods){			
			//(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;IIIIFFF)Z
			if(method.name.equals(ref.get("targetMethod"))
					&& method.desc.equals(String.format("(L%s;L%s;IIIIFFF)Z", 
							ref.get("entityPlayerJavaClassName"), ref.get("worldJavaClassName")))){				
				
				//found method
				LogHelper.info("Patching method " + ref.get("targetMethod"));
				
				LabelNode label1 = new LabelNode(new Label());
				LabelNode label2 = new LabelNode(new Label());
				
				InsnList toInject = new InsnList();
				
				toInject.add(new VarInsnNode(Opcodes.ALOAD, 0));
                toInject.add(new VarInsnNode(Opcodes.ALOAD, 1));
                toInject.add(new VarInsnNode(Opcodes.ALOAD, 2));
                toInject.add(new VarInsnNode(Opcodes.ILOAD, 3));
                toInject.add(new VarInsnNode(Opcodes.ILOAD, 4));
                toInject.add(new VarInsnNode(Opcodes.ILOAD, 5));
                toInject.add(new VarInsnNode(Opcodes.ILOAD, 6));
                toInject.add(new VarInsnNode(Opcodes.FLOAD, 7));
                toInject.add(new VarInsnNode(Opcodes.FLOAD, 8));
                toInject.add(new VarInsnNode(Opcodes.FLOAD, 9));
                toInject.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                		PreloaderReference.EVENT_FACTORY_JAVA_CLASS,
                		"onBlockPlace",
                		String.format("(L%s;L%s;L%s;IIIIFFF)Z", 
                				ref.get("itemStackJavaClassName"),
                				ref.get("entityPlayerJavaClassName"),
                				ref.get("worldJavaClassName"))));
                toInject.add(new JumpInsnNode(Opcodes.IFNE, label2));
                toInject.add(new InsnNode(Opcodes.ICONST_0));
                toInject.add(new InsnNode(Opcodes.IRETURN));
                toInject.add(label2);
                toInject.add(label1);
				
				
				int offset = 0;
				while(method.instructions.get(offset).getOpcode() != Opcodes.ALOAD){
					offset++;
				}
				
				method.instructions.insertBefore(method.instructions.get(offset), toInject);
                CoreLogHelper.info("Finished patching ItemStack");
                break;
			}
		}
		
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(writer);
        return writer.toByteArray();
	}

}
