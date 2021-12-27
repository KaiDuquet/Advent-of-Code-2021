#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <map>

const char* hexToBin(char c)
{
	switch(c)
    {
        case '0': return "0000";
        case '1': return "0001";
        case '2': return "0010";
        case '3': return "0011";
        case '4': return "0100";
        case '5': return "0101";
        case '6': return "0110";
        case '7': return "0111";
        case '8': return "1000";
        case '9': return "1001";
        case 'A': return "1010";
        case 'B': return "1011";
        case 'C': return "1100";
        case 'D': return "1101";
        case 'E': return "1110";
        case 'F': return "1111";
		default: return "";
    }
}

uint64_t parsePacket(const std::string& source, uint32_t* ptr)
{
	uint32_t version = stoi(source.substr(*ptr, 3), nullptr, 2);
	uint32_t id = stoi(source.substr(*ptr + 3, 3), nullptr, 2);
	*ptr += 6;

	if (id == 4)
	{
		std::string binLiteral;
		do
		{
			binLiteral += source.substr(*ptr + 1, 4);
			*ptr += 5;
		} while (source[*ptr - 5] != '0');
		
		return stol(binLiteral, nullptr, 2);
	}
	else
	{
		uint64_t result = 0;
		uint64_t subPacket;
		uint64_t otherSubPacket;

		if (source[(*ptr)++] == '0')
		{
			size_t length = stoi(source.substr(*ptr, 15), nullptr, 2);
			*ptr += 15;
			length += *ptr;
			bool firstSub = true;
			while (*ptr < length)
			{
				switch (id)
				{
				case 0:
					result += parsePacket(source, ptr);
					break;
				case 1:
					if (firstSub) { result = 1; firstSub = false; }
					result *= parsePacket(source, ptr);
					break;
				case 2:
					if (firstSub) { result = INT64_MAX; firstSub = false; }
					subPacket = parsePacket(source, ptr);
					if (subPacket < result) result = subPacket;
					break;
				case 3:
					subPacket = parsePacket(source, ptr);
					if (subPacket > result) result = subPacket;
					break;
				case 5: case 6: case 7:
					subPacket = parsePacket(source, ptr);
					otherSubPacket = parsePacket(source, ptr);
					
					if (id == 5) result = (subPacket > otherSubPacket ? 1 : 0);
					else if (id == 6) result = (subPacket < otherSubPacket ? 1 : 0);
					else result = (subPacket == otherSubPacket ? 1 : 0);
					break;
				}
			}
		}
		else
		{
			size_t amount = stoi(source.substr(*ptr, 11), nullptr, 2);
			*ptr += 11;

			bool firstSub = true;
			for (size_t i = 0; i < amount; i++)
			{
				switch (id)
				{
				case 0:
					result += parsePacket(source, ptr);
					break;
				case 1:
					if (firstSub) { result = 1; firstSub = false; }
					result *= parsePacket(source, ptr);
					break;
				case 2:
					if (firstSub) { result = INT64_MAX; firstSub = false; }
					subPacket = parsePacket(source, ptr);
					if (subPacket < result) result = subPacket;
					break;
				case 3:
					subPacket = parsePacket(source, ptr);
					if (subPacket > result) result = subPacket;
					break;
				case 5: case 6: case 7:
					subPacket = parsePacket(source, ptr);
					otherSubPacket = parsePacket(source, ptr);
					i++;
					
					if (id == 5) result = subPacket > otherSubPacket ? 1 : 0;
					else if (id == 6) result = subPacket < otherSubPacket ? 1 : 0;
					else result = subPacket == otherSubPacket ? 1 : 0;
					break;
				}
			}
		}
		return result;
	}
}

void puzzle1(const std::string& packet)
{
	std::cout << packet.length() << '\n';
	uint32_t ptr = 0;
	uint64_t result = parsePacket(packet, &ptr);
	std::cout << result << std::endl;
}

int main(int argc, char** argv)
{
	std::ifstream infile("data16_1.txt");

	std::string line;
	std::getline(infile, line);

	std::string binPacket;
	for (size_t i = 0; i < line.length(); i++)
		binPacket += hexToBin(line[i]);

	puzzle1(binPacket);

	return 0;
}