class add(object):
    def __init__(self, a, b, id):
        self.a_ = a
        self.b_ = b
        self.id_ = id
        self.answer = [[]]

    def add_self(self):
        self.answer = [[self.a_, self.b_, self.a_ + self.b_], [self.a_ + self.b_, self.a_, self.b_]]
