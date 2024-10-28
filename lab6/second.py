def fit(self, X, Y):

        assert X.shape[0] == Y.shape[0]

        if self.fit_intercept:  # добавляем свободный коэфициент
            X_copy = self._add_intercept(X)
        else:
            X_copy = X.copy()


        # self.weights = np.random.normal(loc=0, scale=1, size=X_copy.shape[1])
        self.weights = np.zeros(X_copy.shape[1])

        previous_weights = self.weights.copy()

        for it in range(self.max_iter):
            batch_indices = np.random.choice(
                np.arange(0, Y_copy.shape[0]), self.batch_size
            )
            X_batch = X_copy[batch_indices, :]
            Y_batch = Y_copy[batch_indices]

            grad_weights = (-X_batch.T).dot(Y_batch - self._sigmoid(X_batch.dot(self.weights)))
            regularization_grad = 2 * self.alpha * self._get_regularization_weights()

            self.weights -= self.lr * grad_weights / self.batch_size
            self.weights -= self.lr * regularization_grad

            if np.linalg.norm(self.weights - previous_weights) < self.delta_converged:
                break
            previous_weights = self.weights.copy()

        self.coef_ = (
            self.weights[:-1] if self.fit_intercept else self.weights
        )  # коэффициенты модели
        self.intercept_ = (
            self.weights[-1] if self.fit_intercept else 0
        )  # свободный коэффициент
        # self.weights состоит из коэффициентов модели и свободного члена
        return self