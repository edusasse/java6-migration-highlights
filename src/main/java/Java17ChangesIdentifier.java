

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TRUCKS-1681
 * 
 * @author FS0192
 *
 */
public class Java17ChangesIdentifier { 

	private static final String IMPORT = "import ";
	private static final String IMPLEMENTS = " implements";
	private static final String EXTENDS = " extends";
	private static final String PACKAGE = "package ";
	final Charset charset = Charset.forName("Cp1252");		
	
	private final String[] changedClassesAndInterfacesSince17 = new String[]{
			"java.util.concurrent.CompletionStage:interface",
			"com.sun.source.doctree.ErroneousTree:interface",
			"java.net.ProtocolFamily:interface",
			"com.sun.source.doctree.DocRootTree:interface",
			"java.util.stream.IntStream:interface",
			"java.util.function.Predicate:interface",
			"javax.lang.model.util.Elements:interface",
			"java.util.function.ObjIntConsumer:interface",
			"java.util.stream.IntStream.Builder:interface",
			"java.nio.file.FileVisitor:interface",
			"java.nio.file.attribute.PosixFileAttributeView:interface",
			"java.util.function.Consumer:interface",
			"java.nio.file.attribute.GroupPrincipal:interface",
			"java.util.function.IntConsumer:interface",
			"java.util.function.UnaryOperator:interface",
			"javax.sound.midi.MidiDeviceTransmitter:interface",
			"com.sun.source.doctree.SinceTree:interface",
			"java.util.function.ToDoubleFunction:interface",
			"com.sun.source.doctree.EntityTree:interface",
			"java.util.stream.Stream:interface",
			"java.time.chrono.ChronoPeriod:interface",
			"java.util.function.DoubleUnaryOperator:interface",
			"com.sun.source.doctree.DeprecatedTree:interface",
			"java.util.function.IntToDoubleFunction:interface",
			"com.sun.source.doctree.EndElementTree:interface",
			"java.util.PrimitiveIterator.OfInt:interface",
			"java.util.function.LongUnaryOperator:interface",
			"java.util.stream:package",
			"com.sun.source.doctree.ThrowsTree:interface",
			"java.util.function.LongPredicate:interface",
			"java.lang.reflect.AnnotatedWildcardType:interface",
			"com.sun.source.doctree.BlockTagTree:interface",
			"java.util.function.DoubleFunction:interface",
			"java.nio.file.attribute.UserDefinedFileAttributeView:interface",
			"java.nio.file.SecureDirectoryStream:interface",
			"java.nio.file.CopyOption:interface",
			"java.nio.file.spi:package",
			"javax.lang.model.type.IntersectionType:interface",
			"com.sun.source.doctree.IdentifierTree:interface",
			"java.nio.channels.SeekableByteChannel:interface",
			"com.sun.source.doctree.DocTree:interface",
			"com.sun.source.doctree.DocCommentTree:interface",
			"java.util.function.ObjDoubleConsumer:interface",
			"com.sun.management.OperatingSystemMXBean:interface",
			"java.lang.reflect.TypeVariable:interface",
			"com.sun.source.doctree.SerialFieldTree:interface",
			"java.nio.file.DirectoryStream.Filter:interface",
			"java.security.KeyStore.Entry.Attribute:interface",
			"com.sun.source.tree.AnnotatedTypeTree:interface",
			"java.util.Spliterator:interface",
			"com.sun.source.tree.UnionTypeTree:interface",
			"java.time.chrono.ChronoZonedDateTime:interface",
			"java.lang.reflect.AnnotatedParameterizedType:interface",
			"java.nio.channels.AsynchronousByteChannel:interface",
			"com.sun.source.doctree.ValueTree:interface",
			"java.nio.file.attribute.FileAttribute:interface",
			"java.util.function.DoubleBinaryOperator:interface",
			"java.util.function.LongConsumer:interface",
			"com.sun.source.doctree.LinkTree:interface",
			"java.lang.management.PlatformLoggingMXBean:interface",
			"com.sun.source.doctree.ReturnTree:interface",
			"com.sun.source.doctree.StartElementTree:interface",
			"com.sun.source.doctree.VersionTree:interface",
			"java.util.Spliterator.OfDouble:interface",
			"java.sql.DriverAction:interface",
			"java.nio.file.attribute:package",
			"javax.sql.CommonDataSource:interface",
			"java.nio.file.attribute.AttributeView:interface",
			"com.sun.tools.attach.VirtualMachine:class",
			"java.security.cert.CertPathValidatorException.Reason:interface",
			"java.util.concurrent.TransferQueue:interface",
			"java.nio.file:package",
			"java.util.function.BooleanSupplier:interface",
			"java.util.PrimitiveIterator:interface",
			"java.util.function.LongToDoubleFunction:interface",
			"java.util.PrimitiveIterator.OfDouble:interface",
			"java.nio.file.attribute.BasicFileAttributes:interface",
			"com.sun.source.tree.MethodTree:interface",
			"com.sun.source.doctree.InlineTagTree:interface",
			"java.security.cert.CertPathChecker:interface",
			"java.util.stream.Collector:interface",
			"java.nio.file.PathMatcher:interface",
			"java.awt.SecondaryLoop:interface",
			"java.security.AlgorithmConstraints:interface",
			"java.util.function.IntSupplier:interface",
			"com.sun.nio.sctp.Notification:interface",
			"com.sun.source.doctree.SeeTree:interface",
			"com.sun.source.doctree.UnknownBlockTagTree:interface",
			"java.util.function.Supplier:interface",
			"java.nio.file.attribute.BasicFileAttributeView:interface",
			"java.nio.file.DirectoryStream:interface",
			"java.util.function.DoublePredicate:interface",
			"java.lang.AutoCloseable:interface",
			"java.time.temporal.TemporalAdjuster:interface",
			"java.util.function.IntToLongFunction:interface",
			"java.time.temporal.TemporalAccessor:interface",
			"java.util.stream.LongStream:interface",
			"com.sun.javadoc.ExecutableMemberDoc:interface",
			"com.sun.nio.sctp:package",
			"java.lang.invoke:package",
			"java.util.function.ToIntBiFunction:interface",
			"java.util.function.DoubleSupplier:interface",
			"com.sun.source.util.DocSourcePositions:interface",
			"java.util.function.IntBinaryOperator:interface",
			"java.util.function.LongBinaryOperator:interface",
			"com.sun.source.tree.IntersectionTypeTree:interface",
			"com.sun.source.doctree.AttributeTree:interface",
			"java.util.function.IntUnaryOperator:interface",
			"com.sun.javadoc.Type:interface",
			"java.util.function.ToLongBiFunction:interface",
			"com.sun.source.util.Plugin:interface",
			"com.sun.source.doctree.SerialTree:interface",
			"java.sql.Connection:interface",
			"java.util.PrimitiveIterator.OfLong:interface",
			"java.util.function.IntFunction:interface",
			"java.util.function.Function:interface",
			"java.time.temporal.TemporalField:interface",
			"java.util.function.BiFunction:interface",
			"java.nio.file.WatchEvent:interface",
			"java.util.function.BinaryOperator:interface",
			"java.nio.channels.SocketChannel:class",
			"com.sun.source.doctree.LiteralTree:interface",
			"java.util.concurrent.CompletableFuture.AsynchronousCompletionTask:interface",
			"java.util.function.LongFunction:interface",
			"java.nio.file.attribute.AclFileAttributeView:interface",
			"com.sun.security.jgss.ExtendedGSSCredential:interface",
			"java.nio.channels.CompletionHandler:interface",
			"java.util.Map.Entry:interface",
			"java.sql.SQLType:interface",
			"com.sun.nio.sctp.NotificationHandler:interface",
			"com.sun.source.doctree.AuthorTree:interface",
			"java.nio.file.Path:interface",
			"java.util.stream.Stream.Builder:interface",
			"com.sun.source.doctree.SerialDataTree:interface",
			"java.nio.channels.NetworkChannel:interface",
			"java.util.function.DoubleConsumer:interface",
			"java.net.SocketOption:interface",
			"java.util.Spliterator.OfLong:interface",
			"java.nio.file.OpenOption:interface",
			"java.util.Spliterator.OfPrimitive:interface",
			"java.nio.file.attribute.FileOwnerAttributeView:interface",
			"java.nio.file.WatchEvent.Modifier:interface",
			"java.util.stream.BaseStream:interface",
			"java.nio.file.attribute.PosixFileAttributes:interface",
			"java.lang.reflect.AnnotatedArrayType:interface",
			"java.util.function.BiPredicate:interface",
			"java.nio.file.attribute.DosFileAttributeView:interface",
			"java.time.temporal.TemporalUnit:interface",
			"java.time.chrono.ChronoLocalDate:interface",
			"java.util.function.ObjLongConsumer:interface",
			"com.sun.source.doctree:package",
			"java.nio.file.attribute.UserPrincipal:interface",
			"java.time.chrono.Chronology:interface",
			"java.nio.channels.spi.SelectorProvider:class",
			"java.nio.file.Watchable:interface",
			"java.lang.invoke.MethodHandleInfo:interface",
			"java.nio.file.WatchService:interface",
			"java.util.function.DoubleToLongFunction:interface",
			"java.util.function.LongToIntFunction:interface",
			"com.sun.source.tree.MemberReferenceTree:interface",
			"com.sun.source.doctree.CommentTree:interface",
			"com.sun.source.util.JavacTask:class",
			"com.sun.source.doctree.ReferenceTree:interface",
			"java.time.temporal.Temporal:interface",
			"java.lang.management.BufferPoolMXBean:interface",
			"java.nio.file.attribute.DosFileAttributes:interface",
			"javax.sound.midi.MidiDeviceReceiver:interface",
			"java.util.function.ToLongFunction:interface",
			"java.util.function.ToDoubleBiFunction:interface",
			"java.nio.channels.MulticastChannel:interface",
			"java.util.Spliterator.OfInt:interface",
			"javax.lang.model.type.UnionType:interface",
			"java.time.temporal.TemporalAmount:interface",
			"com.sun.source.doctree.TextTree:interface",
			"java.util.stream.DoubleStream:interface",
			"java.util.function.IntPredicate:interface",
			"java.util.spi.ResourceBundleControlProvider:interface",
			"javax.lang.model.type.ExecutableType:interface",
			"java.nio.file.WatchEvent.Kind:interface",
			"java.sql.Driver:interface",
			"java.util.stream.LongStream.Builder:interface",
			"javax.lang.model.type.TypeVisitor:interface",
			"java.time.temporal.TemporalQuery:interface",
			"com.sun.source.doctree.DocTreeVisitor:interface",
			"javax.lang.model.element.Parameterizable:interface",
			"com.sun.source.doctree.InheritDocTree:interface",
			"com.sun.source.doctree.ParamTree:interface",
			"java.lang.reflect.AnnotatedType:interface",
			"java.nio.file.attribute.FileAttributeView:interface",
			"java.util.function.LongSupplier:interface",
			"java.security.cert.Extension:interface",
			"java.nio.channels.AsynchronousChannel:interface",
			"java.util.function:package",
			"java.nio.channels.DatagramChannel:class",
			"javax.lang.model.element.ExecutableElement:interface",
			"java.lang.management.PlatformManagedObject:interface",
			"java.nio.channels.ServerSocketChannel:class",
			"javax.swing.plaf.nimbus:package",
			"javax.lang.model.AnnotatedConstruct:interface",
			"java.nio.file.attribute.FileStoreAttributeView:interface",
			"com.sun.nio.sctp.SctpSocketOption:interface",
			"java.time.chrono.Era:interface",
			"java.util.function.BiConsumer:interface",
			"java.time.chrono.ChronoLocalDateTime:interface",
			"java.util.function.ToIntFunction:interface",
			"java.util.stream.DoubleStream.Builder:interface",
			"javax.lang.model.element.QualifiedNameable:interface",
			"java.util.function.DoubleToIntFunction:interface",
			"javax.swing.plaf.synth.SynthUI:interface",
			"com.sun.source.tree.TypeParameterTree:interface",
			"javax.sql.rowset.RowSetFactory:interface",
			"com.sun.javadoc.AnnotatedType:interface",
			"java.lang.reflect.AnnotatedTypeVariable:interface",
			"java.nio.file.WatchKey:interface",
			"com.sun.source.doctree.UnknownInlineTagTree:interface"
			};
	
	public File[] listf(String directoryName) throws CommentRemoverException {

	    // .............list file
	    File directory = new File(directoryName);

	    // get all the files from a directory
	    File[] fList = directory.listFiles();

	    for (File file : fList) {
	        if (file.isFile()) {
	            if (file.getName().toLowerCase().endsWith(".java")){
	            	run(file);
	            }
	        } else if (file.isDirectory()) {
	            listf(file.getAbsolutePath());
	        }
	    }
	    
	    return fList;
	}
	private Map<String, String> mapChangedInterfaces = new HashMap<String, String>();
	private Map<String, String> mapChangedClasses = new HashMap<String, String>();
	
	private Map<String, Data> mapData = new HashMap<String, Data>();
	
	class Data {
		boolean isInterface = false;
		public List<String> listOfOccurrences = new ArrayList<String>();
		public Integer count = 0;
		
		int incrementCount(){
			return ++count;
		}
	}
	
	public Java17ChangesIdentifier() throws ClassNotFoundException{
		for (String clazz : changedClassesAndInterfacesSince17){
			Class<?> clazzClass = null;
			try {
				clazzClass = Class.forName(clazz.split(":")[0]);
				if (clazzClass.isInterface()){
					mapChangedInterfaces.put(clazzClass.getSimpleName(), clazzClass.getCanonicalName());
					mapChangedInterfaces.put(clazzClass.getCanonicalName(), clazzClass.getCanonicalName());
				} else {
					mapChangedClasses.put(clazzClass.getSimpleName(), clazzClass.getCanonicalName());
					mapChangedClasses.put(clazzClass.getCanonicalName(), clazzClass.getCanonicalName());
				}
			} catch (Exception e){
				String type = clazz.split(":")[1];
				if ("interface".equals(type)){
					mapChangedInterfaces.put(clazz.split(":")[0].split("\\.")[clazz.split(":")[0].split("\\.").length-1], clazz.split(":")[0]);
					mapChangedInterfaces.put(clazz.split(":")[0], clazz.split(":")[0]);
				} else {
					mapChangedClasses.put(clazz.split(":")[0].split("\\.")[clazz.split(":")[0].split("\\.").length-1], clazz.split(":")[0]);
					mapChangedClasses.put(clazz.split(":")[0], clazz.split(":")[0]);
				}
			}
		}
	}
	
	public static void main(String args[]) throws CommentRemoverException, ClassNotFoundException {
		System.out.println("Reading " + args[0] + "..");
		try {
			Java17ChangesIdentifier j7 = new Java17ChangesIdentifier();
			j7.listf(args[0]);
			j7.report();
		} finally {
			System.out.println("Finished!");
		}
	}
	
	public void report(){
		mapData.forEach((k,v)->{
			System.out.println( (v.isInterface ? "Interface: " : "Class: " ) + k + " Count : " + v.count);
			v.listOfOccurrences.forEach(item->{
				System.out.println("\t" + item);
			});
		});
	}
	
	public void run(File f) throws CommentRemoverException{		 
		try (BufferedReader br = new BufferedReader(
				   new InputStreamReader(
		                      new FileInputStream(f), charset))) {

			StringBuilder classStringBuild = new StringBuilder();
			String classString = null;
			String className = f.getName().replaceAll(".java", "");
			String classPackage = null;
			String interfaces = null, extendss = null;
			String line;
			while ((line = br.readLine()) != null) {
				classStringBuild.append(line + "\n");
			}
			classString = removeComments(classStringBuild);
			int indexOfPackage = classString.indexOf(PACKAGE);
			int indexOfImplements = classString.indexOf(IMPLEMENTS);
			int indexOfExtends = classString.indexOf(EXTENDS);
			if (indexOfPackage > -1){
				classPackage = classString.substring(indexOfPackage + PACKAGE.length(), classString.indexOf(";", indexOfPackage));
			}
			if (indexOfImplements > -1){
				interfaces = classString.substring(indexOfImplements + IMPLEMENTS.length(), classString.indexOf("{", indexOfImplements));
				if (interfaces != null && interfaces.trim().length() > 0){
					interfaces = interfaces.replaceAll(" ", "").replaceAll("\n", ""); 
					String[] interfacesArray = interfaces.split(",");
					for (String inter : interfacesArray){
						
						String[] interAux = inter.split("\\.");
						String completeInterfaceName = null;
						if (interAux.length == 1){
							try {
								int i = classString.lastIndexOf(IMPORT, classString.indexOf(interAux[0]));
								completeInterfaceName = classString.substring(i + IMPORT.length(), classString.indexOf(";", i));
							} catch (Exception e){}
						} else {
							completeInterfaceName = inter;
						}
						String key = completeInterfaceName;
						try {
							if (key == null){
								key = interAux[interAux.length - 1].replaceAll("\n", "").trim();
							}
						} catch (Exception e){
							e.printStackTrace();
						}
						String interfaceChangedJava17 = mapChangedInterfaces.get(key);
						if (interfaceChangedJava17 != null){
							Data data = mapData.get(key);
							if (data == null){
								data = new Data();
							}
							data.isInterface = true;
							data.incrementCount();
							data.listOfOccurrences.add(f.getCanonicalPath());
							mapData.put(key, data);
						}
					}
				}
			}
			
			if (indexOfExtends > -1){
				if (classString.indexOf("{") > indexOfExtends){
					extendss = classString.substring(indexOfExtends + EXTENDS.length(), classString.indexOf("{", indexOfExtends));
					extendss = extendss.indexOf(IMPLEMENTS) > -1 ? extendss.substring(0, extendss.indexOf(IMPLEMENTS)) : extendss;
					if (extendss != null && extendss.trim().length() > 0){
						extendss = extendss.replaceAll(" ", ""); 
						String[] extendssArray = extendss.split(",");
						for (String clz : extendssArray){
							
							String[] clzAux = clz.split("\\.");
							String key = null;
							try {
								key = clzAux[clzAux.length - 1].replaceAll("\n", "").trim();
							} catch (Exception e){
								e.printStackTrace();
							}
							String clazzeChangedJava17 = mapChangedClasses.get(key);
							if (clazzeChangedJava17 != null){
								Data data = mapData.get(clzAux);
								if (data == null){
									data = new Data();
								}
								data.incrementCount();
								data.listOfOccurrences.add(f.getCanonicalPath());
								mapData.put(key, data);
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	} 
	
	public String removeComments(StringBuilder fileContent) throws CommentRemoverException {
		String regex = RegexSelector.getRegexByFileType("java");
		Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileContent);
		JavaFileProcessor jfp = new JavaFileProcessor(new CommentRemover.CommentRemoverBuilder()
				.removeJava(true)
				.removeTodos(true) // Remove todos
	            .removeSingleLines(true) // Do not remove single line type comments
	            .removeMultiLines(true) // Remove multiple type comments
	            .preserveJavaClassHeaders(false) // Preserves class header comment
	            .preserveCopyRightHeaders(false) // Preserves copyright comment
				.build());
		return jfp.doRemoveOperation(fileContent, matcher).toString();
  }
	
	
}