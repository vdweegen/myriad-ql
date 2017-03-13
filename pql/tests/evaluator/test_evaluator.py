# coding=utf-8
from tests.shared import Shared


class TestEvaluator(Shared):

    def test_eval_arithmetic_both_integers(self):
        input_string = """
        form taxOfficeExample {
            "Did you sell a house in 2010?" hasSoldHouse: integer = 10 + 2
        }
        """
        environment = self.apply_evaluate(input_string)
        expected_identifier = 'hasSoldHouse'
        self.assertTrue(expected_identifier in environment, "Environment should contain key hasSoldHouse")
        self.assertEqual(12, environment[expected_identifier], "Evaluation should result in 12")

    def test_eval_arithmetic_integer_and_money(self):
        input_string = """
        form taxOfficeExample {
            "Did you sell a house in 2010?" hasSoldHouse: integer = 10 + 2.0
        }
        """
        environment = self.apply_evaluate(input_string)
        expected_identifier = 'hasSoldHouse'
        self.assertTrue(expected_identifier in environment, "Environment should contain key hasSoldHouse")
        self.assertEqual(12.0, environment[expected_identifier], "Evaluation should result in 12.0")

    def test_eval_arithmetic_double_field(self):
        input_string = """
        form taxOfficeExample {
            "Did you sell a house in 2010?" hasSoldHouse: integer = 10 + 2
            "Price with inflation" inflationPrice: integer = hasSoldHouse * 1.1
        }
        """
        environment = self.apply_evaluate(input_string)
        expected_identifier_1 = 'hasSoldHouse'
        self.assertTrue(expected_identifier_1 in environment, "Environment should contain key hasSoldHouse")
        self.assertEqual(12, environment[expected_identifier_1], "Evaluation should result in 12")

        expected_identifier_2 = 'inflationPrice'
        self.assertTrue(expected_identifier_2 in environment, "Environment should contain key inflationPrice")
        self.assertEqual(float((10 + 2) * 1.1), environment[expected_identifier_2], "Evaluation should result in 13.2")

    def test_eval_boolean_and_simple(self):
        input_string = """
        form taxOfficeExample {
            "Did you sell a house in 2010?" hasSoldHouse: boolean = true && false
        }
        """
        environment = self.apply_evaluate(input_string)
        expected_identifier_1 = 'hasSoldHouse'
        self.assertTrue(expected_identifier_1 in environment, "Environment should contain key hasSoldHouse")
        self.assertFalse(environment[expected_identifier_1], "Evaluation should result in false")

    def test_eval_boolean_and_or(self):
        input_string = """
        form taxOfficeExample {
            "Did you sell a house in 2010?" hasSoldHouse: boolean = true
            "Did you sell a house in 2011?" hasSoldHouse11: boolean = (hasSoldHouse || false) && false
        }
        """
        environment = self.apply_evaluate(input_string)
        expected_identifier_1 = 'hasSoldHouse'
        self.assertTrue(expected_identifier_1 in environment, "Environment should contain key hasSoldHouse")
        self.assertTrue(environment[expected_identifier_1], "Evaluation should result in false")

        expected_identifier_2 = 'hasSoldHouse11'
        self.assertFalse(environment[expected_identifier_2], "Evaluation should result in false")

    def test_eval_boolean_greater_than(self):
        input_string = """
        form taxOfficeExample {
        "House selling price?" sellingPrice: money = 23000.87
        "House selling price, adjusted for inflation?" sellingPriceWithInflation: money = 23000.87 * 1.1
        "Did you pay more because of inflation?" wasMoreExpensive: boolean = sellingPriceWithInflation >= sellingPrice
        }
        """
        environment = self.apply_evaluate(input_string)
        expected_identifier_1 = 'sellingPrice'
        self.assertTrue(expected_identifier_1 in environment, "Environment should contain key hasSoldHouse")
        self.assertTrue(environment[expected_identifier_1], "Evaluation should result in false")

        expected_identifier_2 = 'sellingPriceWithInflation'
        self.assertTrue(expected_identifier_2 in environment, "Environment should contain key hasSoldHouse")
        self.assertEqual(float(23000.87 * 1.1), environment[expected_identifier_2], "Output should equal 25300.957")

        expected_identifier_3 = 'wasMoreExpensive'
        self.assertTrue(expected_identifier_3 in environment, "Environment should contain key hasSoldHouse")
        self.assertTrue(environment[expected_identifier_3], "Evaluation should result in true")
