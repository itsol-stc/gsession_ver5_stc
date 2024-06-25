package jp.groupsession.v2.api.biz;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.io.IOTools;
/**
 *
 * <br>[機  能] WEB API設定ファイルに関する各種操作を提供するビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiConfFileBiz {
    /** WEB API パッケージ */
    private static final String API_PACKAGE__ = "jp.groupsession.v2.api";
    /** 「WEB API Formクラス一覧」設定ファイル名 */
    private static final String FILE_NAME_FORMLIST__ = "apilist.conf";

    /**
     * <br>[機  能] 設定ファイルからWEP APIのFormクラス一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションルートパス
     * @return Formクラス一覧
     * @throws IOException
     */
    public List<String> getApiClassList(String appRootPath)
    throws IOException {
        List<String> classList = new ArrayList<String>();

        String filePath = appRootPath
                        + "/WEB-INF/plugin/api/"
                        + FILE_NAME_FORMLIST__;

        String confValue = IOTools.readText(filePath, Encoding.UTF_8);
        StringTokenizer st = new StringTokenizer(confValue, "\r\n");
        while (st.hasMoreTokens()) {
            classList.add(st.nextToken());
        }

        return classList;
    }

    /**
     * <br>[機  能] 指定したパッケージ直下の"Formクラス"を取得する
     * <br>[解  説]
     * <br>[備  考] 下位パッケージ内のクラスも対象とする
     * @param packageName パッケージ名称
     * @param classList クラス一覧
     * @return "Formクラス"一覧
     * @throws IOException 指定パッケージ内のリソース取得に失敗
     * @throws ClassNotFoundException 指定クラスの取得に失敗
     */
    protected List<String> _getFormClasseList(String packageName, List<String> classList)
            throws IOException, ClassNotFoundException {

        //クラスを格納するListを生成
        List<String> formClassList = new ArrayList<String>();
        if (classList != null) {
            formClassList.addAll(classList);
        }

        // パッケージ内のリソースを取得
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> urlEnum = classLoader.getResources(packageName.replace(".", "/"));

        //クラス一覧を取得
        while (urlEnum.hasMoreElements()) {
            URL url = urlEnum.nextElement();
            File dir = new File(url.getPath());

            for (String path : dir.list()) {
                //Formクラス("Form.class"で終わるファイル)のみを取得対象とする
                if (path.endsWith("Form.class")) {
                    formClassList.add(packageName
                                    + "."
                                    + path.substring(0, path.length() - 6));
                } else if (path.indexOf(".") < 0) {
                    //下位パッケージの検査を実施
                    formClassList = _getFormClasseList(packageName + "." + path, formClassList);
                }
            }
        }

        return formClassList;
    }

    /**
     * <br>[機  能] mainメソッド
     * <br>[解  説]
     * <br>[備  考] WEB APIのbuild時,「WEB APIのFormクラス」を設定ファイルに記載する処理に使用する
     * @param args コマンドライン引数
     * @throws Exception 例外発生
     */
    public static void main(String[] args) {

        if (args == null || args.length <= 0) {
            System.out.println("引数にルートパスが指定されていません。");
            return;
        }

        try {
            //WEB APIの"Form"クラス一覧を取得する
            ApiConfFileBiz biz = new ApiConfFileBiz();
            List<String> classNameList = biz._getFormClasseList(API_PACKAGE__, null);

            //クラス一覧を設定ファイル書き込み用に成型する
            StringBuilder sb = new StringBuilder("");
            for (String className : classNameList) {

                if (sb.length() > 0) {
                    sb.append("\r\n");
                }
                sb.append(className);
            }

            //設定ファイルにクラス一覧を書き込む
            String filePath = args[0];
            filePath = filePath.replaceAll("\\\\", "/");
            if (!filePath.endsWith(File.separator)) {
                filePath += "/";
            }

            filePath += FILE_NAME_FORMLIST__;
            BufferedWriter out = null;
            try {
                out = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(filePath),
                                                    Encoding.UTF_8));
                out.write(sb.toString());
            } catch (IOException e) {
                throw e;
            } finally {
                if (out != null) {
                    out.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
