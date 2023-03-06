class Matrix {
    double[] Values;

    Matrix(double[] Values) {
        this.Values = Values;
    }

    Matrix multiply(Matrix diff) {
        double[] result = new double[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    result[i * 3 + j] +=
                            this.Values[i * 3 + k] * diff.Values[k * 3 + j];

                }
            }
        }
        return new Matrix(result);
    }

    Vectex transform(Vectex trans) {
        return new Vectex(
                trans.x * Values[0] + trans.y * Values[3] + trans.z * Values[6],
                trans.x * Values[1] + trans.y * Values[4] + trans.z * Values[7],
                trans.x * Values[2] + trans.y * Values[5] + trans.z * Values[8]);
    }
}
