package com.qwert2603.andrlib.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes(value = {"com.qwert2603.andrlib.generator.GenerateListChanger"})
public class GenerateListChangerProcessor extends AbstractProcessor {

    private static final String GENERATED_PACKAGE = "com.qwert2603.andrlib.generated";

    private static String createFileText(String cases) {
        return "package " + GENERATED_PACKAGE + "\n" +
                "\n" +
                "import com.qwert2603.andrlib.base.mvi.load_refresh.list.ListModel\n" +
                "import com.qwert2603.andrlib.base.mvi.load_refresh.list.ListModelChanger\n" +
                "import com.qwert2603.andrlib.base.mvi.load_refresh.list.ListViewState\n" +
                "\n" +
                "open class ListModelChangerImpl : ListModelChanger {\n" +
                "    @Suppress(\"UNCHECKED_CAST\")\n" +
                "    open override fun <VS : ListViewState<*>> changeListModel(vs: VS, listModel: ListModel): VS = when (vs) {\n" +
                cases +
                "        else -> throw java.lang.IllegalArgumentException(\"ListModelChangerImpl changeListModel $vs $listModel\")\n" +
                "    } as VS\n" +
                "}";
    }

    private static String createCaseText(TypeElement typeElement) {
        String typeParameters;
        if (typeElement.getTypeParameters().isEmpty()) {
            typeParameters = "";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('<');
            for (int i = 0; i < typeElement.getTypeParameters().size(); i++) {
                stringBuilder.append("*, ");
            }
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
            stringBuilder.append('>');
            typeParameters = stringBuilder.toString();
        }
        return "        is " + typeElement.getQualifiedName().toString() + typeParameters + " -> vs.copy(listModel = listModel)\n";
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            final FileWriter fileWriter = new FileWriter(new File(Utils.getDirName(processingEnv), "qqq.txt"), true);
            fileWriter.write("errorRaised=" + roundEnv.errorRaised()
                    + "; processingOver=" + roundEnv.processingOver()
                    + "; roundEnv.getElementsAnnotatedWith(GenerateListChanger.class)=" + roundEnv.getElementsAnnotatedWith(GenerateListChanger.class).toString() + "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ignored) {
        }

        try {
            final FileWriter fileWriter = new FileWriter(new File(Utils.getDirName(processingEnv), "www.txt"), true);
            fileWriter.write("processingEnv.getOptions()=" + processingEnv.getOptions().toString() + "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ignored) {
        }

//        final String filenameLastMillis = "last_list.mls";
//        if (System.currentTimeMillis() - Utils.getMillisFromFile(processingEnv, filenameLastMillis) < 15000) {
//            return true;
//        }
//        Utils.saveMillisToFile(processingEnv, filenameLastMillis, System.currentTimeMillis());

        if (roundEnv.processingOver()) return true;

        StringBuilder cases = new StringBuilder();
        for (Element element : roundEnv.getElementsAnnotatedWith(GenerateListChanger.class)) {
            cases.append(createCaseText(((TypeElement) element)));
        }
        try {
            String dir = Utils.getDirName(processingEnv);
            String fileName = "ListModelChangerImpl.kt";
            Writer writer = new FileWriter(new File(dir, fileName));
            writer.write(createFileText(cases.toString()));
            writer.flush();
            writer.close();
        } catch (IOException throwable) {
            throw new RuntimeException(throwable);
        }

        return true;
    }
}
