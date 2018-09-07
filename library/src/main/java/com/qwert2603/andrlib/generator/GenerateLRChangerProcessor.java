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
@SupportedAnnotationTypes(value = {"com.qwert2603.andrlib.generator.GenerateLRChanger"})
public class GenerateLRChangerProcessor extends AbstractProcessor {

    private static final String GENERATED_PACKAGE = "com.qwert2603.andrlib.generated";

    private static String createFileText(String cases) {
        return "package " + GENERATED_PACKAGE + "\n" +
                "\n" +
                "import com.qwert2603.andrlib.base.mvi.load_refresh.LRModel\n" +
                "import com.qwert2603.andrlib.base.mvi.load_refresh.LRModelChanger\n" +
                "import com.qwert2603.andrlib.base.mvi.load_refresh.LRViewState\n" +
                "\n" +
                "open class LRModelChangerImpl : LRModelChanger {\n" +
                "    @Suppress(\"UNCHECKED_CAST\")\n" +
                "    open override fun <VS : LRViewState> changeLRModel(vs: VS, lrModel: LRModel): VS = when (vs) {\n" +
                cases +
                "        else -> throw java.lang.IllegalArgumentException(\"LRModelChangerImpl changeLRModel $vs $lrModel\")\n" +
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
        return "        is " + typeElement.getQualifiedName().toString() + typeParameters + " -> vs.copy(lrModel = lrModel)\n";
    }

    private boolean done = false;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (done) return true;
        done = true;

        StringBuilder cases = new StringBuilder();
        for (Element element : roundEnv.getElementsAnnotatedWith(GenerateLRChanger.class)) {
            cases.append(createCaseText(((TypeElement) element)));
        }
        try {
            String dir = Utils.getDirName(processingEnv);
            String fileName = "LRModelChangerImpl.kt";
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
