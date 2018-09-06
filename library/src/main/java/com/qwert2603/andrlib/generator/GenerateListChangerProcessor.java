package com.qwert2603.andrlib.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
                "class ListModelChangerImpl : ListModelChanger {\n" +
                "    @Suppress(\"UNCHECKED_CAST\")\n" +
                "    override fun <VS : ListViewState<*>> changeListModel(vs: VS, listModel: ListModel): VS = when (vs) {\n" +
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
        StringBuilder cases = new StringBuilder();
        for (Element element : roundEnv.getElementsAnnotatedWith(GenerateListChanger.class)) {
            cases.append(createCaseText(((TypeElement) element)));
        }
        try {
            String dir = processingEnv.getOptions().get("kapt.kotlin.generated").replace("kaptKotlin", "kapt");

            File lastFile = new File(dir, "last_list.mls");
            try {
                long lastMillis = Long.parseLong(new BufferedReader(new FileReader(lastFile)).readLine());
                if (System.currentTimeMillis() - lastMillis < 15000) return true;
            } catch (Exception ignored) {
            }
            FileWriter lastFileWriter = new FileWriter(lastFile);
            lastFileWriter.write(String.valueOf(System.currentTimeMillis()));
            lastFileWriter.flush();

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
