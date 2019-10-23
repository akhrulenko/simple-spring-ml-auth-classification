package app;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import smile.classification.SVM;
import smile.math.kernel.HellingerKernel;

public class Classificator {

	private SVM<double[]> svm;

	public Classificator() {
		double gamma = 1 / (10 * 0.04561);
		double C = 5.0;

		double[][] X = new double[540][10];
		int[] y = new int[540];

		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("FullSet.csv"),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int i = 0;
		for (String line : lines) {
			String[] featuresString = line.split(",");
			int j = 0;
			for (String feature : featuresString) {
				X[i][j] = Double.valueOf(feature);
				j++;
			}
			i++;
		}

		for (int k = 0; k < 192; k++) {
			y[k] = 0;
		}
		for (int k = 192; k < 336; k++) {
			y[k] = 1;
		}
		for (int k = 336; k < 408; k++) {
			y[k] = 2;
		}
		for (int k = 408; k < 432; k++) {
			y[k] = 3;
		}
		for (int k = 432; k < 540; k++) {
			y[k] = 4;
		}

		svm = new SVM<>(new HellingerKernel(), C, 5, SVM.Multiclass.ONE_VS_ONE);

		svm.learn(X, y);
		svm.finish();
	}

	public int classify(int password, int ip, int httpHeaders, int cookie, int tryNum, int js, int userAgent,
			int reqPerMin, int period, int entryTime) {
//		int retClass = -1;
//		String args = " " + password + " " + ip + " " + httpHeaders + " " + cookie + " " + tryNum + " " + js + " "
//				+ userAgent + " " + reqPerMin + " " + period + " " + entryTime;
//		try {
//			Process pythonScript = Runtime.getRuntime().exec("python classificator.py" + args);
//			pythonScript.waitFor();
//			BufferedReader br = new BufferedReader(new InputStreamReader(pythonScript.getInputStream()));
//
//			retClass = Integer.valueOf(br.readLine().substring(1, 2));
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return retClass - 1;

		return svm.predict(new double[] { password, ip, httpHeaders, cookie, tryNum, js, userAgent, reqPerMin, period,
				entryTime });
	}
}