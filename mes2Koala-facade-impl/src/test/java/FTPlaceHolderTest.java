import org.junit.Test;
import org.seu.acetec.mes2Koala.core.common.ResourcesUtil;

/**
 * Created by LCN on 2016/4/6.
 */
public class FTPlaceHolderTest {

    @Test
    public void test() {
        String worker = ResourcesUtil.getValue("placeholder", "Worker");

        String str = "hello world $Worker$</p></li></$Worker$ol></td></tr></tbody></table><p><br/></p>";
        str = str.replace(worker, "lucannan");
        System.out.println(worker);
        System.out.println(str);

    }
}
