UPDATE task SET priority_id = (SELECT id FROM priority WHERE name = 'urgently');