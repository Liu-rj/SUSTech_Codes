# run the following codes line by line, in an interactive shell
import pandas as pd
# First please put the CSV file "winequality-red.csv" in the same folder as this script
wine = pd.read_csv("winequality-red.csv",sep=";")

# Print the column names
wine.columns

# Show the first 10 instances (rows)
wine.head(10)

# Missing values check
wine_values_ravel = wine.values.ravel()
import numpy as np
print "number of missing values:", len(wine_values_ravel[wine_values_ravel==np.nan])

# Print the attributes statistics
wine.describe()

# Another way to show the attributes statistics, by boxplot
# Must install seaborn first
import seaborn as sns
%matplotlib inline
sns.boxplot(x = "quality", y = "pH", data = wine) # quality vs. pH

# please change pH to other attributes, e.g., quality vs. alcohol
sns.boxplot(x = "quality", y = "alcohol", data = wine) # alcohol contributes a lot to quality

# Correlation between two attributes
sns.lmplot(x = "fixed acidity", y = "pH", data = wine)

# Correlations between any two variables, diagonal is a histogram for distribution
sns.pairplot(wine)

# Histogram plot of "quality"
wine["quality"].value_counts().sort_index().plot(kind="bar")

# Predict quality using decision tree
from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.cross_validation import cross_val_score
from sklearn.metrics import f1_score
tree_clf = DecisionTreeClassifier()
X = wine.iloc[:,0:-1]
Y = wine["quality"]

# Train the tree model according to "accuracy" score
acc_tree = cross_val_score(tree_clf,X,Y,cv=10,scoring="accuracy")

# Print the mean and std result of accuracy
print acc_tree.mean()
print acc_tree.std()

# Train the tree model according to "precision" score
precision_tree = cross_val_score(tree_clf,X,Y,cv=10,scoring="precision_micro")
print precision_tree.mean()
print precision_tree.std()

# Train the tree model according to "F1" score
f1_tree = cross_val_score(tree_clf,X,Y,cv=10,scoring="f1_micro")
print f1_tree.mean()
print f1_tree.std()

# Train the random forest model according to "accuracy" score
rf_model = RandomForestClassifier(n_estimators=100)
scores_rf = cross_val_score(rf_model,X,Y,cv=10,scoring="accuracy")
print scores_rf.mean()
print scores_rf.std()

# Variables' importances in random forest
rf = RandomForestClassifier(n_estimators=100)
rf.fit(X,Y)
feature_imp = pd.Series(rf.feature_importances_)
feature_imp.index = X.columns
sorteds_features = feature_imp.sort_values(ascending=False)
sorteds_features

# Convert the feature importance vector into a DataFrame
feature_impDF = pd.DataFrame()
feature_impDF["importance"] = rf.feature_importances_
feature_impDF["feature"] = X.columns
feature_impDF.sort_values("importance",ascending=False,inplace=True)
feature_impDF

# Plot the sorted feature importance
sorteds_features.plot(kind="bar")

# Grid search by cross validation for tuning parameters
from sklearn.grid_search import GridSearchCV
import numpy as np
rf2 = RandomForestClassifier()
# make pair of (n_estimators, criterion), n_estimators = 10, 20, ..., 90, 100, criterion = "gini" or "entropy"
params = {"n_estimators":np.arange(10,100,10),"criterion":["gini","entropy"]}
grid_rf2 = GridSearchCV(estimator=rf2, param_grid=params, cv=5,scoring="accuracy")

#Train the model
grid_rf2.fit(X,Y)

# Print the best accuracy and the corresponding parameters
print grid_rf2.best_score_ 
print grid_rf2.best_params_ 

#Adaboost step result
from sklearn.ensemble import AdaBoostClassifier
adaboost = AdaBoostClassifier(n_estimators = 50)
# Split the data into training set (70%) and test set (30%)
from sklearn import cross_validation
X_train, X_test, Y_train, Y_test = cross_validation.train_test_split(X, Y, test_size=0.3, random_state=42)

# Train the adaboost model
adaboost.fit(X_train,Y_train) 
from sklearn.metrics import accuracy_score

# Compute the predict quality by test data at each stage in adaboost
y_preds_test = list(adaboost.staged_predict(X_test))

# Compute the accuracy score at each stage
accuracy_tests = [] 
for y_pred in y_preds_test:
    accuracy_tests.append(accuracy_score(Y_test,y_pred))

# Compute the predict quality by training data at each stage in adaboost
y_preds_train = list(adaboost.staged_predict(X_train))
accuracy_train = [] 
for y_pred in y_preds_train:
    accuracy_train.append(accuracy_score(Y_train,y_pred))

# Compare the training and test errors by plot
df = pd.DataFrame()
df["test"] = accuracy_tests
df["train"] = accuracy_train
df.plot()

# Performance compare: random forest vs. adaboost
# Model training and test using random forest
rf1 = RandomForestClassifier(n_estimators=100)
rf1.fit(X_train,Y_train)
from sklearn.metrics import classification_report, confusion_matrix
Y_predict_rf = rf1.predict(X_test)
print classification_report(Y_test, Y_predict_rf)
print confusion_matrix(Y_test, Y_predict_rf)
print 'Accuracy: ', accuracy_score(Y_test, Y_predict_rf)

# Model training and test using adaboost
Y_predict_ada = adaboost.predict(X_test)
print classification_report(Y_test, Y_predict_ada)
print confusion_matrix(Y_test, Y_predict_ada)
print 'Accuracy: ', accuracy_score(Y_test, Y_predict_ada)

# Both two methods perform badly
# Compute the average margin for random forest
average_margin = 0
total_num = len(X_test)
for x,y in zip(X_test,Y_test):
    correct_num = 0
    wrong_num = 0
    for tree in rf1.estimators_:
        if tree.predict(x.reshape(1,11))+3 == y:
            correct_num += 1
        else:
            wrong_num += 1
    average_margin += (correct_num - wrong_num) / (correct_num + wrong_num)
print 'Average margin: ', average_margin / total_num

# Compute the out-of-bag error rate
import matplotlib.pyplot as plt
plt.style.use('ggplot')
RANDOM_STATE = 123
rf2 = RandomForestClassifier(warm_start=True,max_features=None,oob_score=True,random_state=RANDOM_STATE)
min_estimators = 15
max_estimators = 175
pair_list = []
for i in range(min_estimators, max_estimators+1):
    rf2.set_params(n_estimators=i)
    rf2.fit(X_train, Y_train)
    oob_error = 1 - rf2.oob_score_
    pair_list.append((i,oob_error))
xs,ys = zip(*pair_list)
plt.plot(xs,ys)
plt.xlim(min_estimators,max_estimators)
plt.xlabel('n_estimators')
plt.ylabel('OOB error rate')
plt.legend(loc='upper right')

######Try to balance the data distribution using over sampling approach
######First you need to install the "imbalanced-learn" package, this needs scipy version >= 0.17
######uncomment and run the following code
# pip install -U imbalanced-learn
# from imblearn.over_sampling import RandomOverSampler
# X = wine.iloc[:,:-1].values
# Y = wine['quality'].values
# ros = RandomOverSampler()
# X,Y = ros.fit_sample(X,Y)
# pd.DataFrame(Y)[0].value_counts().sort_index().plot(kind="bar")
