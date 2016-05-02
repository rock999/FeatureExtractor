/**
 * Created by tobias.meier on 30.04.2016.
 */
package ba.ciel5.featureExtractor.features;

import ba.ciel5.featureExtractor.utils.AbstractSyntaxTreeUtil;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class NGramFeatureGroupTest {
    @org.junit.Test
    public void extract() throws Exception {
        NGramFeatureGroup feature = new NGramFeatureGroup();
        char[] code = TestClass1.getTestCode().toCharArray();
        CompilationUnit ast = AbstractSyntaxTreeUtil.parse(code);
        Map<String, Double> result = feature.extract(null, null, ast, code);

        assertEquals(13.0, result.get("ExpressionStatement"), 0.0);
        assertEquals(21.0, result.get("FieldAccess"), 0.0);
        assertEquals(19.0, result.get("FieldAccess-ThisExpression"), 0.0);
        assertEquals(11.0, result.get("ThisExpression-SimpleName-SimpleName"), 0.0);
    }
}